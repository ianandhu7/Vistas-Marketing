import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'
import {
  checkMobileExists,
  sendOtpExistingUser,
  sendOtpNewUser,
  verifyOtpExistingUser,
  verifyOtpNewUser,
  registerNewUser
} from '../api'

describe('API Service', () => {
  beforeEach(() => {
    vi.stubGlobal('fetch', vi.fn())
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('checkMobileExists calls correct endpoint', async () => {
    global.fetch.mockResolvedValueOnce({
      json: async () => ({ data: true })
    })

    const res = await checkMobileExists('9876543210')
    expect(global.fetch).toHaveBeenCalledWith(
      'https://api-prod.vistaslearning.com/api/v1/lead/checkMobileNumberExistence?mobile=9876543210'
    )
    expect(res.data).toBe(true)
  })

  it('sendOtpExistingUser calls correct endpoint with POST', async () => {
    global.fetch.mockResolvedValueOnce({
      json: async () => ({ status: true })
    })

    const res = await sendOtpExistingUser('9876543210')
    expect(global.fetch).toHaveBeenCalledWith(
      'https://api-prod.vistaslearning.com/api/v1/auth/login/send-otp?mobileNumber=9876543210',
      { method: 'POST' }
    )
    expect(res.status).toBe(true)
  })

  it('sendOtpNewUser calls correct endpoint with name encoded', async () => {
    global.fetch.mockResolvedValueOnce({
      json: async () => ({ data: true })
    })

    const res = await sendOtpNewUser('9876543210', 'John Doe & Son')
    expect(global.fetch).toHaveBeenCalledWith(
      'https://api-prod.vistaslearning.com/api/v1/lead/sendotp?mobile=9876543210&name=John%20Doe%20%26%20Son',
      { method: 'POST' }
    )
    expect(res.data).toBe(true)
  })

  it('verifyOtpExistingUser sends correct JSON body', async () => {
    global.fetch.mockResolvedValueOnce({
      json: async () => ({ status: true })
    })

    const res = await verifyOtpExistingUser('9876543210', '123456')
    expect(global.fetch).toHaveBeenCalledWith(
      'https://api-prod.vistaslearning.com/api/v1/auth/login/verify',
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          mobileNumber: '9876543210',
          otp: '123456',
          password: null
        })
      }
    )
    expect(res.status).toBe(true)
  })

  it('verifyOtpNewUser parses response text and handles empty response', async () => {
    global.fetch.mockResolvedValueOnce({
      ok: true,
      text: async () => '{"data":true}'
    })

    let res = await verifyOtpNewUser('9876543210', '123456')
    expect(global.fetch).toHaveBeenCalledWith(
      'https://api-prod.vistaslearning.com/api/v1/lead/verifyotp?mobile=9876543210&otp=123456',
      { method: 'POST' }
    )
    expect(res.data).toBe(true)

    // Handle empty text response
    global.fetch.mockResolvedValueOnce({
      ok: true,
      text: async () => ''
    })

    res = await verifyOtpNewUser('9876543210', '123456')
    expect(res).toEqual({})
  })

  it('verifyOtpNewUser throws error on non-ok status', async () => {
    global.fetch.mockResolvedValueOnce({
      ok: false,
      status: 400,
      text: async () => 'Bad Request details'
    })

    await expect(verifyOtpNewUser('9876543210', '123456')).rejects.toThrow('Bad Request details')
  })

  it('registerNewUser sends correct registration payload', async () => {
    global.fetch.mockResolvedValueOnce({
      json: async () => ({ statusCode: 200 })
    })

    const res = await registerNewUser('9876543210', 'Jane', '123456')
    expect(global.fetch).toHaveBeenCalledWith(
      'https://api-prod.vistaslearning.com/api/v3/auth/new-auth-signup',
      {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          firstName: 'Jane',
          lastName: 'null',
          username: '9876543210',
          email: null,
          password: null,
          mobileNumber: '9876543210',
          role: 'Student',
          idSyllabus: -1,
          idMedium: -1,
          idClassStandard: -1,
          idSecondaryLanguage: -1,
          idState: -1,
          deviceId: '',
          otp: '123456',
          plan: null,
          platform: 'web',
          couponCode: null
        })
      }
    )
    expect(res.statusCode).toBe(200)
  })
})
