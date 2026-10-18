// Tests for ../firestore.rules against the Firestore emulator.
// Run with `npm test` in this folder; it starts the emulator for the run.
//
// "app flow" tests mirror the reads and writes the Android app makes today
// (data/repository/*), so they must keep passing. "denied" tests are the holes
// the old catch-all rule left open.

import { readFileSync } from 'node:fs';
import { after, before, beforeEach, describe, test } from 'node:test';
import {
  assertFails,
  assertSucceeds,
  initializeTestEnvironment,
} from '@firebase/rules-unit-testing';
import {
  addDoc,
  collection,
  deleteDoc,
  doc,
  getDoc,
  getDocs,
  limit,
  query,
  setDoc,
  setLogLevel,
  updateDoc,
  where,
} from 'firebase/firestore';

// The "denied" tests make the SDK log every PERMISSION_DENIED; keep the output readable.
setLogLevel('silent');

let env;

const profile = (uid, role = 'user') => ({
  firstName: 'First',
  secondName: 'Second',
  mobilePhone: '123456789',
  email: `${uid}@example.com`,
  role,
});

const application = (uid) => ({
  dataToSend: {
    education: 'Bachelor',
    programmes: [{ universityID: 'uni1', programmeName: 'CS', city: 'Aarhus',
      programmeType: 'BSc', programmeLength: '3', description: '' }],
  },
  userID: uid,
  answered: false,
});

// Signed-in users. The email claim matches what Firebase Auth puts in the token.
const as = (uid) => env.authenticatedContext(uid, { email: `${uid}@example.com` }).firestore();
const anon = () => env.unauthenticatedContext().firestore();

before(async () => {
  env = await initializeTestEnvironment({
    projectId: 'demo-studiawdanii',
    firestore: {
      rules: readFileSync(new URL('../firestore.rules', import.meta.url), 'utf8'),
      host: '127.0.0.1',
      port: 8080,
    },
  });
});

after(async () => {
  await env?.cleanup();
});

beforeEach(async () => {
  await env.clearFirestore();
  await env.withSecurityRulesDisabled(async (ctx) => {
    const db = ctx.firestore();
    await setDoc(doc(db, 'users/admin'), profile('admin', 'admin'));
    await setDoc(doc(db, 'users/alice'), profile('alice'));
    await setDoc(doc(db, 'users/bob'), profile('bob'));
    await setDoc(doc(db, 'university_info/uni1'), { universityName: 'AU', description: '' });
    await setDoc(doc(db, 'progrmmes/p1'), { programmeName: 'CS', programmeCity: 'Aarhus',
      universityID: 'uni1', description: '', programmeLength: '3', programmeType: 'BSc' });
    await setDoc(doc(db, 'junction_programme_university/j1'), { universityID: 'uni1', programmeID: 'p1' });
    await setDoc(doc(db, 'user_programmes_university/up-alice'), { userID: 'alice', programmeID: 'p1' });
    await setDoc(doc(db, 'user_programmes_university/up-bob'), { userID: 'bob', programmeID: 'p1' });
    await setDoc(doc(db, 'user_data_to_send/app-alice'), application('alice'));
    await setDoc(doc(db, 'step_one_status/alice'), { userID: 'alice', status: 'Sent' });
  });
});

describe('users', () => {
  test('app flow: registration creates own profile with role "user"', async () => {
    await assertSucceeds(setDoc(doc(as('carol'), 'users/carol'), profile('carol')));
  });

  test('app flow: saving the profile again keeps the role', async () => {
    await assertSucceeds(setDoc(doc(as('alice'), 'users/alice'), profile('alice')));
  });

  test('app flow: a user reads their own profile (role check after sign-in)', async () => {
    await assertSucceeds(getDoc(doc(as('alice'), 'users/alice')));
    await assertSucceeds(getDoc(doc(as('admin'), 'users/admin')));
  });

  test("app flow: an admin reads an applicant's profile", async () => {
    await assertSucceeds(getDoc(doc(as('admin'), 'users/alice')));
  });

  test('denied: registering as an admin', async () => {
    await assertFails(setDoc(doc(as('carol'), 'users/carol'), profile('carol', 'admin')));
  });

  test('denied: promoting yourself to admin', async () => {
    await assertFails(setDoc(doc(as('alice'), 'users/alice'), profile('alice', 'admin')));
    await assertFails(updateDoc(doc(as('alice'), 'users/alice'), { role: 'admin' }));
  });

  test('denied: an admin demoted by a profile save', async () => {
    await assertFails(setDoc(doc(as('admin'), 'users/admin'), profile('admin', 'user')));
  });

  test("denied: reading or writing someone else's profile", async () => {
    await assertFails(getDoc(doc(as('alice'), 'users/bob')));
    await assertFails(setDoc(doc(as('alice'), 'users/bob'), profile('bob')));
  });

  test('denied: profile with a different email or extra fields', async () => {
    await assertFails(setDoc(doc(as('carol'), 'users/carol'), { ...profile('carol'), email: 'x@example.com' }));
    await assertFails(setDoc(doc(as('carol'), 'users/carol'), { ...profile('carol'), isAdmin: true }));
  });

  test('denied: listing users or deleting a profile', async () => {
    await assertFails(getDocs(collection(as('admin'), 'users')));
    await assertFails(deleteDoc(doc(as('alice'), 'users/alice')));
  });
});

describe('catalog', () => {
  for (const path of ['university_info/uni1', 'progrmmes/p1', 'junction_programme_university/j1']) {
    test(`app flow: signed-in users read ${path.split('/')[0]}`, async () => {
      await assertSucceeds(getDoc(doc(as('alice'), path)));
    });

    test(`denied: writing ${path.split('/')[0]}, even as admin`, async () => {
      await assertFails(setDoc(doc(as('admin'), path), { hacked: true }));
      await assertFails(deleteDoc(doc(as('alice'), path)));
    });
  }

  test('app flow: list universities and query programmes of a university', async () => {
    await assertSucceeds(getDocs(collection(as('alice'), 'university_info')));
    await assertSucceeds(getDocs(query(collection(as('alice'), 'junction_programme_university'),
      where('universityID', '==', 'uni1'))));
  });
});

describe('user_programmes_university', () => {
  const col = (db) => collection(db, 'user_programmes_university');

  test('app flow: select, list and remove own programmes', async () => {
    const db = as('alice');
    await assertSucceeds(setDoc(doc(col(db)), { userID: 'alice', programmeID: 'p2' }));
    await assertSucceeds(getDocs(query(col(db), where('userID', '==', 'alice'))));
    await assertSucceeds(getDocs(query(col(db), where('userID', '==', 'alice'), where('programmeID', '==', 'p1'))));
    await assertSucceeds(deleteDoc(doc(db, 'user_programmes_university/up-alice')));
  });

  test('denied: selecting a programme for someone else', async () => {
    await assertFails(setDoc(doc(col(as('alice'))), { userID: 'bob', programmeID: 'p2' }));
  });

  test("denied: reading or deleting someone else's selection", async () => {
    await assertFails(getDocs(col(as('alice'))));
    await assertFails(getDoc(doc(as('alice'), 'user_programmes_university/up-bob')));
    await assertFails(deleteDoc(doc(as('alice'), 'user_programmes_university/up-bob')));
  });
});

describe('user_data_to_send', () => {
  const col = (db) => collection(db, 'user_data_to_send');

  test('app flow: a student checks for and sends one application', async () => {
    const db = as('bob');
    await assertSucceeds(getDocs(query(col(db), where('userID', '==', 'bob'))));
    await assertSucceeds(setDoc(doc(col(db)), application('bob')));
  });

  test('app flow: an admin lists all applications and marks one answered', async () => {
    const db = as('admin');
    await assertSucceeds(getDocs(col(db)));
    await assertSucceeds(updateDoc(doc(db, 'user_data_to_send/app-alice'), { answered: true }));
  });

  test('denied: a student lists all applications or reads another one', async () => {
    await assertFails(getDocs(col(as('bob'))));
    await assertFails(getDoc(doc(as('bob'), 'user_data_to_send/app-alice')));
  });

  test('denied: sending for someone else or as already answered', async () => {
    await assertFails(addDoc(col(as('bob')), application('alice')));
    await assertFails(addDoc(col(as('bob')), { ...application('bob'), answered: true }));
  });

  test('denied: a student answering or editing an application', async () => {
    await assertFails(updateDoc(doc(as('alice'), 'user_data_to_send/app-alice'), { answered: true }));
    await assertFails(deleteDoc(doc(as('alice'), 'user_data_to_send/app-alice')));
  });

  test('denied: an admin changing anything but answered', async () => {
    await assertFails(updateDoc(doc(as('admin'), 'user_data_to_send/app-alice'), { userID: 'bob' }));
  });
});

describe('step_one_status', () => {
  test('app flow: a student sets and reads their own "Sent" status', async () => {
    await assertSucceeds(setDoc(doc(as('bob'), 'step_one_status/bob'), { userID: 'bob', status: 'Sent' }));
    await assertSucceeds(getDocs(query(collection(as('alice'), 'step_one_status'),
      where('userID', '==', 'alice'), limit(1))));
  });

  test('app flow: a second Send can still reset an answered status to "Sent" (fixed in 1.5)', async () => {
    await env.withSecurityRulesDisabled((ctx) =>
      setDoc(doc(ctx.firestore(), 'step_one_status/alice'), { userID: 'alice', status: 'Accepted' }));
    await assertSucceeds(setDoc(doc(as('alice'), 'step_one_status/alice'), { userID: 'alice', status: 'Sent' }));
  });

  test('app flow: an admin accepts or cancels', async () => {
    await assertSucceeds(updateDoc(doc(as('admin'), 'step_one_status/alice'), { status: 'Accepted' }));
    await assertSucceeds(updateDoc(doc(as('admin'), 'step_one_status/alice'), { status: 'Cancelled' }));
  });

  test('denied: a student accepting themselves', async () => {
    await assertFails(setDoc(doc(as('alice'), 'step_one_status/alice'), { userID: 'alice', status: 'Accepted' }));
    await assertFails(updateDoc(doc(as('alice'), 'step_one_status/alice'), { status: 'Accepted' }));
  });

  test("denied: a student touching someone else's status", async () => {
    await assertFails(setDoc(doc(as('bob'), 'step_one_status/alice'), { userID: 'alice', status: 'Sent' }));
    await assertFails(getDoc(doc(as('bob'), 'step_one_status/alice')));
    await assertFails(getDocs(collection(as('bob'), 'step_one_status')));
  });

  test('denied: an admin writing another status value or field', async () => {
    await assertFails(updateDoc(doc(as('admin'), 'step_one_status/alice'), { status: 'Whatever' }));
    await assertFails(updateDoc(doc(as('admin'), 'step_one_status/alice'), { userID: 'bob' }));
  });
});

describe('edge cases (from the 0.6 review)', () => {
  test('app flow: a new user without a profile yet can look for their own doc', async () => {
    await assertSucceeds(getDoc(doc(as('carol'), 'users/carol')));
  });

  test('app flow: the profile email matches the token regardless of case', async () => {
    const db = env.authenticatedContext('dave', { email: 'dave@example.com' }).firestore();
    await assertSucceeds(setDoc(doc(db, 'users/dave'), { ...profile('dave'), email: 'Dave@Example.com' }));
  });

  test('app flow: an old profile without a role can be saved as "user"', async () => {
    await env.withSecurityRulesDisabled((ctx) => {
      const { role, ...noRole } = profile('erin');
      return setDoc(doc(ctx.firestore(), 'users/erin'), noRole);
    });
    await assertSucceeds(setDoc(doc(as('erin'), 'users/erin'), profile('erin')));
  });

  test('app flow: an admin reads a status document', async () => {
    await assertSucceeds(getDoc(doc(as('admin'), 'step_one_status/alice')));
  });

  test('denied: a profile with a missing, null or non-string field', async () => {
    const { mobilePhone, ...missing } = profile('carol');
    await assertFails(setDoc(doc(as('carol'), 'users/carol'), missing));
    await assertFails(setDoc(doc(as('carol'), 'users/carol'), { ...profile('carol'), firstName: null }));
    await assertFails(setDoc(doc(as('carol'), 'users/carol'), { ...profile('carol'), mobilePhone: 123 }));
  });

  test('denied: role "Admin" with a capital A is not an admin', async () => {
    await env.withSecurityRulesDisabled((ctx) =>
      setDoc(doc(ctx.firestore(), 'users/frank'), profile('frank', 'Admin')));
    await assertFails(getDocs(collection(as('frank'), 'user_data_to_send')));
    await assertFails(updateDoc(doc(as('frank'), 'step_one_status/alice'), { status: 'Accepted' }));
  });

  test('denied: a signed-in user with no profile listing applications', async () => {
    await assertFails(getDocs(collection(as('carol'), 'user_data_to_send')));
  });

  test('denied: own status written with another userID or an extra field', async () => {
    await assertFails(setDoc(doc(as('alice'), 'step_one_status/alice'), { userID: 'bob', status: 'Sent' }));
    await assertFails(setDoc(doc(as('alice'), 'step_one_status/alice'), { userID: 'alice', status: 'Sent', note: 'x' }));
  });

  test('denied: an admin setting "Sent", creating or deleting a status', async () => {
    await assertFails(updateDoc(doc(as('admin'), 'step_one_status/alice'), { status: 'Sent' }));
    await assertFails(setDoc(doc(as('admin'), 'step_one_status/bob'), { userID: 'bob', status: 'Accepted' }));
    await assertFails(deleteDoc(doc(as('admin'), 'step_one_status/alice')));
  });

  test('denied: an admin un-answering an application', async () => {
    await assertFails(updateDoc(doc(as('admin'), 'user_data_to_send/app-alice'), { answered: false }));
  });

  test('denied: a malformed application', async () => {
    const col = collection(as('bob'), 'user_data_to_send');
    await assertFails(addDoc(col, { ...application('bob'), extra: 1 }));
    await assertFails(addDoc(col, { ...application('bob'), dataToSend: 'text' }));
    await assertFails(addDoc(col, { ...application('bob'), dataToSend: { education: 'B', programmes: [], x: 1 } }));
    const { answered, ...noAnswered } = application('bob');
    await assertFails(addDoc(col, noAnswered));
  });

  test('denied: editing a selection or adding extra fields to one', async () => {
    await assertFails(updateDoc(doc(as('alice'), 'user_programmes_university/up-alice'), { programmeID: 'p9' }));
    await assertFails(addDoc(collection(as('alice'), 'user_programmes_university'),
      { userID: 'alice', programmeID: 'p2', extra: true }));
  });
});

describe('signed out', () => {
  test('denied: every read and write', async () => {
    const db = anon();
    await assertFails(getDoc(doc(db, 'university_info/uni1')));
    await assertFails(getDoc(doc(db, 'users/alice')));
    await assertFails(getDocs(collection(db, 'user_data_to_send')));
    await assertFails(setDoc(doc(db, 'users/mallory'), profile('mallory')));
  });
});
