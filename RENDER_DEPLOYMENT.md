# Deploying Vistas Project on Render (Step-by-Step Guide)

To host your project on Render for testing, you need to deploy the **Backend (as a Web Service)** and the **Frontend (as a Static Site)**.

Follow these steps to deploy:

---

## Step 1: Push Your Code to GitHub
Ensure all code changes (including the newly created `backend/middleware.js` file for CORS support) are pushed to a repository on GitHub.
```bash
git add .
git commit -m "Configure production CORS middleware and deployment assets"
git push origin main
```

---

## Step 2: Deploy the Next.js Backend (Web Service)
1. Log in to [Render](https://dashboard.render.com/).
2. Click **New +** and select **Web Service**.
3. Connect your GitHub repository.
4. Set the following configuration options:
   * **Name:** `vistas-backend`
   * **Region:** Choose the region closest to your users (e.g., `Singapore` or `Oregon`).
   * **Branch:** `main` (or whichever branch you push to).
   * **Root Directory:** `backend` *(CRITICAL: This tells Render to only build the backend folder)*.
   * **Runtime:** `Node`
   * **Build Command:** `npm install && npm run build`
   * **Start Command:** `npm run start`
   * **Instance Type:** Select the **Free** tier.

5. Click **Advanced** and add the following **Environment Variables**:
   * `DATABASE_URL` = `postgresql://...` *(Your Neon database connection URL)*
   * `JWT_SECRET` = `(Your JWT secret string)`
   * `DEMO_MODE` = `true`
   * `FIREBASE_PROJECT_ID` = `vistas-f546e`
   * `FIREBASE_CLIENT_EMAIL` = `firebase-adminsdk-fbsvc@vistas-f546e.iam.gserviceaccount.com`
   * `FIREBASE_PRIVATE_KEY` = `(Your full Firebase private key, including begin/end lines)`
   * `RAZORPAY_KEY_ID` = `rzp_test_SpXyqz94mMFONq`
   * `RAZORPAY_KEY_SECRET` = `cuKknQRNEw665KydMJBbFUDa`
   * `ALLOWED_ORIGINS` = `https://your-frontend-name.onrender.com` *(Replace this with the URL Render assigns to your Frontend in Step 3)*

6. Click **Create Web Service**.

---

## Step 3: Deploy the Vue Frontend (Static Site)
1. Go back to the Render Dashboard.
2. Click **New +** and select **Static Site**.
3. Connect the same GitHub repository.
4. Set the following configuration options:
   * **Name:** `vistas-frontend`
   * **Branch:** `main`
   * **Root Directory:** Leave empty (uses the root folder).
   * **Build Command:** `npm install && npm run build`
   * **Publish Directory:** `dist`

5. Click **Advanced** and add the following **Environment Variables**:
   * `VITE_API_URL` = `https://vistas-backend.onrender.com/api` *(Replace with the URL of the Backend Web Service you created in Step 2, followed by `/api`)*
   * `VITE_DEMO_MODE` = `true`
   * `VITE_RAZORPAY_KEY_ID` = `rzp_test_SpXyqz94mMFONq`
   * `VITE_FIREBASE_API_KEY` = `AIzaSyBVZbq-aN7k1mX_PXegWyh5fpzmaO3Jqw0`
   * `VITE_FIREBASE_AUTH_DOMAIN` = `vistas-f546e.firebaseapp.com`
   * `VITE_FIREBASE_PROJECT_ID` = `vistas-f546e`
   * `VITE_FIREBASE_STORAGE_BUCKET` = `vistas-f546e.firebasestorage.app`
   * `VITE_FIREBASE_MESSAGING_SENDER_ID` = `750753364317`
   * `VITE_FIREBASE_APP_ID` = `1:750753364317:web:f271ae4691198402f210b4`
   * `VITE_FIREBASE_MEASUREMENT_ID` = `G-FEFCQX8EXZ`

6. Click **Create Static Site**.

---

## Important Security/Config Notes:
* **CORS Setup:** We have renamed `backend/proxy.js` to `backend/middleware.js` and updated the function to `middleware`. In Next.js, this automatically configures CORS headers. Now, your frontend static site can securely talk to the backend.
* **Spin-up Time:** On Render's Free tier, the backend web service spins down after 15 minutes of inactivity. When you visit the app after a long time, the first API request (login/otp) might take 50 seconds to respond while the server wakes up.
