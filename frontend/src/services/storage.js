export function saveUserToLocalStorage(data) {
  const subFlag = data.subscriptionFlag
  const isSubscribed =
    subFlag === true ||
    (subFlag && subFlag.subscribedFlag === true)
  const isTrial =
    subFlag && subFlag.trialFlag === true

  const authData = {
    accessToken: data.accessToken,
    refreshToken: data.refreshToken,
    userSurId: String(data.userSurId),
    firstName: data.firstName || '',
    lastName: data.lastName || '',
    mobileNumber: data.mobileNumber || '',
    email: data.email || '',
    userProfilePic: data.userProfilePic || '',
    registeredAs: data.registeredAs || 'Student',
    isSubscribed,
    isTrial,
    syllabus: data.syllabusObject || null,
    classStandard: data.classStandardObject || null,
    state: data.stateObject || null,
    userData: data
  }

  localStorage.setItem('vista-auth', JSON.stringify(authData))
}

export function clearUserFromLocalStorage() {
  localStorage.removeItem('vista-auth')

  // also clear legacy keys for safety / migration cleanup
  const keys = [
    'accessToken', 'refreshToken', 'userSurId',
    'firstName', 'lastName', 'mobileNumber',
    'email', 'userProfilePic', 'registeredAs',
    'isSubscribed', 'isTrial', 'syllabus',
    'classStandard', 'state', 'userData',
    'authToken', 'user', 'subscriptionStatus', 'userPhone'
  ]
  keys.forEach(k => localStorage.removeItem(k))
}

export function getUserFromLocalStorage() {
  try {
    const authData = JSON.parse(localStorage.getItem('vista-auth') || 'null')
    if (authData) {
      return authData
    }
  } catch (e) {
    console.error('Error parsing vista-auth:', e)
  }
  return {
    accessToken: null,
    refreshToken: null,
    userSurId: null,
    firstName: '',
    lastName: '',
    mobileNumber: '',
    email: '',
    userProfilePic: '',
    isSubscribed: false,
    isTrial: false,
    registeredAs: 'Student',
    userData: null
  }
}

export function isLoggedIn() {
  const auth = getUserFromLocalStorage()
  return !!auth.accessToken
}

export function isSubscribed() {
  const auth = getUserFromLocalStorage()
  return auth.isSubscribed === true
}
