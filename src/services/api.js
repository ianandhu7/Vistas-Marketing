const BASE_URL = 'https://api-prod.vistaslearning.com'

export async function checkMobileExists(mobile) {
  const res = await fetch(
    `${BASE_URL}/api/v1/lead/checkMobileNumberExistence?mobile=${mobile}`
  )
  return await res.json()
}

export async function sendOtpExistingUser(mobile) {
  const res = await fetch(
    `${BASE_URL}/api/v1/auth/login/send-otp?mobileNumber=${mobile}`,
    { method: 'POST' }
  )
  return await res.json()
}

export async function sendOtpNewUser(mobile, name) {
  const res = await fetch(
    `${BASE_URL}/api/v1/lead/sendotp?mobile=${mobile}&name=${encodeURIComponent(name)}`,
    { method: 'POST' }
  )
  return await res.json()
}

export async function verifyOtpExistingUser(mobile, otp) {
  const res = await fetch(
    `${BASE_URL}/api/v1/auth/login/verify`,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        mobileNumber: mobile,
        otp: otp,
        password: null
      })
    }
  )
  return await res.json()
}

export async function verifyOtpNewUser(mobile, otp) {
  const res = await fetch(
    `${BASE_URL}/api/v1/lead/verifyotp?mobile=${mobile}&otp=${otp}`,
    { method: 'POST' }
  )

  const text = await res.text()

  if (!res.ok) {
    throw new Error(text || `Verify OTP failed with status ${res.status}`)
  }

  return text ? JSON.parse(text) : {}
}

export async function registerNewUser(mobile, name, otp) {
  const res = await fetch(
    `${BASE_URL}/api/v3/auth/new-auth-signup`,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        firstName: name,
        lastName: 'null',
        username: mobile,
        email: null,
        password: null,
        mobileNumber: mobile,
        role: 'Student',
        idSyllabus: -1,
        idMedium: -1,
        idClassStandard: -1,
        idSecondaryLanguage: -1,
        idState: -1,
        deviceId: '',
        otp: otp,
        plan: null,
        platform: 'web',
        couponCode: null
      })
    }
  )
  return await res.json()
}
