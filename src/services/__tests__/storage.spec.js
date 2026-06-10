import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'
import {
  saveUserToLocalStorage,
  clearUserFromLocalStorage,
  getUserFromLocalStorage,
  isLoggedIn,
  isSubscribed
} from '../storage'

describe('Storage Service', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  it('saveUserToLocalStorage writes correct fields to vista-auth and derives isSubscribed/isTrial', () => {
    const mockUser = {
      accessToken: 'token-123',
      refreshToken: 'refresh-456',
      userSurId: 789,
      firstName: 'John',
      lastName: 'Doe',
      mobileNumber: '9876543210',
      email: 'john@example.com',
      userProfilePic: 'pic.jpg',
      registeredAs: 'Student',
      subscriptionFlag: {
        subscribedFlag: true,
        trialFlag: false
      },
      syllabusObject: { id: 1 },
      classStandardObject: { id: 2 },
      stateObject: { id: 3 }
    }

    saveUserToLocalStorage(mockUser)

    const saved = JSON.parse(localStorage.getItem('vista-auth'))
    expect(saved.accessToken).toBe('token-123')
    expect(saved.refreshToken).toBe('refresh-456')
    expect(saved.userSurId).toBe('789') // derived as string
    expect(saved.firstName).toBe('John')
    expect(saved.lastName).toBe('Doe')
    expect(saved.isSubscribed).toBe(true)
    expect(saved.isTrial).toBe(false)
  })

  it('saveUserToLocalStorage correctly derives isSubscribed from direct boolean', () => {
    const mockUser = {
      accessToken: 'token-123',
      userSurId: 789,
      subscriptionFlag: true
    }

    saveUserToLocalStorage(mockUser)

    const saved = JSON.parse(localStorage.getItem('vista-auth'))
    expect(saved.isSubscribed).toBe(true)
  })

  it('saveUserToLocalStorage correctly derives isTrial when active', () => {
    const mockUser = {
      accessToken: 'token-123',
      userSurId: 789,
      subscriptionFlag: {
        subscribedFlag: false,
        trialFlag: true
      }
    }

    saveUserToLocalStorage(mockUser)

    const saved = JSON.parse(localStorage.getItem('vista-auth'))
    expect(saved.isSubscribed).toBe(false)
    expect(saved.isTrial).toBe(true)
  })

  it('getUserFromLocalStorage returns defaults when empty or malformed', () => {
    localStorage.setItem('vista-auth', 'invalid-json')
    let user = getUserFromLocalStorage()
    expect(user.accessToken).toBeNull()
    expect(user.isSubscribed).toBe(false)

    localStorage.removeItem('vista-auth')
    user = getUserFromLocalStorage()
    expect(user.accessToken).toBeNull()
    expect(user.isSubscribed).toBe(false)
  })

  it('getUserFromLocalStorage returns parsed data when exists', () => {
    const mockAuth = { accessToken: 'token-abc', isSubscribed: true }
    localStorage.setItem('vista-auth', JSON.stringify(mockAuth))

    const user = getUserFromLocalStorage()
    expect(user.accessToken).toBe('token-abc')
    expect(user.isSubscribed).toBe(true)
  })

  it('clearUserFromLocalStorage removes vista-auth and legacy keys', () => {
    localStorage.setItem('vista-auth', '{"token":"1"}')
    localStorage.setItem('accessToken', '123')
    localStorage.setItem('subscriptionStatus', 'active')

    clearUserFromLocalStorage()

    expect(localStorage.getItem('vista-auth')).toBeNull()
    expect(localStorage.getItem('accessToken')).toBeNull()
    expect(localStorage.getItem('subscriptionStatus')).toBeNull()
  })

  it('isLoggedIn returns true when accessToken exists', () => {
    expect(isLoggedIn()).toBe(false)

    localStorage.setItem('vista-auth', JSON.stringify({ accessToken: 'token-123' }))
    expect(isLoggedIn()).toBe(true)
  })

  it('isSubscribed returns true when isSubscribed flag is true', () => {
    expect(isSubscribed()).toBe(false)

    localStorage.setItem('vista-auth', JSON.stringify({ isSubscribed: true }))
    expect(isSubscribed()).toBe(true)
  })
})
