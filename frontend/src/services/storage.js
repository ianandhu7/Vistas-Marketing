export function saveUserToLocalStorage(data) {
  localStorage.setItem('accessToken', data.accessToken)
  localStorage.setItem('refreshToken', data.refreshToken)
  localStorage.setItem('userSurId', String(data.userSurId))
  localStorage.setItem('firstName', data.firstName || '')
  localStorage.setItem('lastName', data.lastName || '')
  localStorage.setItem('mobileNumber', data.mobileNumber || '')
  localStorage.setItem('email', data.email || '')
  localStorage.setItem('userProfilePic', data.userProfilePic || '')
  localStorage.setItem('registeredAs', data.registeredAs || 'Student')

  const subFlag = data.subscriptionFlag
  const isSubscribed =
    subFlag === true ||
    (subFlag && subFlag.subscribedFlag === true)
  const isTrial =
    subFlag && subFlag.trialFlag === true

  localStorage.setItem('isSubscribed', String(isSubscribed))
  localStorage.setItem('isTrial', String(isTrial))

  if (data.syllabusObject) {
    localStorage.setItem('syllabus', JSON.stringify(data.syllabusObject))
  }
  if (data.classStandardObject) {
    localStorage.setItem('classStandard', JSON.stringify(data.classStandardObject))
  }
  if (data.stateObject) {
    localStorage.setItem('state', JSON.stringify(data.stateObject))
  }

  localStorage.setItem('userData', JSON.stringify(data))
}

export function clearUserFromLocalStorage() {
  const keys = [
    'accessToken', 'refreshToken', 'userSurId',
    'firstName', 'lastName', 'mobileNumber',
    'email', 'userProfilePic', 'registeredAs',
    'isSubscribed', 'isTrial', 'syllabus',
    'classStandard', 'state', 'userData',
    // old keys from previous version
    'authToken', 'user', 'subscriptionStatus', 'userPhone'
  ]
  keys.forEach(k => localStorage.removeItem(k))
}

export function getUserFromLocalStorage() {
  return {
    accessToken:    localStorage.getItem('accessToken'),
    refreshToken:   localStorage.getItem('refreshToken'),
    userSurId:      localStorage.getItem('userSurId'),
    firstName:      localStorage.getItem('firstName'),
    lastName:       localStorage.getItem('lastName'),
    mobileNumber:   localStorage.getItem('mobileNumber'),
    email:          localStorage.getItem('email'),
    userProfilePic: localStorage.getItem('userProfilePic'),
    isSubscribed:   localStorage.getItem('isSubscribed') === 'true',
    isTrial:        localStorage.getItem('isTrial') === 'true',
    registeredAs:   localStorage.getItem('registeredAs'),
    userData:       JSON.parse(localStorage.getItem('userData') || 'null')
  }
}

export function isLoggedIn() {
  return !!localStorage.getItem('accessToken')
}

export function isSubscribed() {
  return localStorage.getItem('isSubscribed') === 'true'
}
