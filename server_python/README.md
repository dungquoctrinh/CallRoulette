CallRoulette-Python
===

CallRoulette is our Android Demo for using Twilio Client + Android Studio. This is the accompanying TwiML web app written in Python + Flask. This script does 3 things

1. Generates Capability token for our android client

2. Creates and maintains a queue for callers

3. Connects a new caller to a caller in the queue

Prerequisites
---

Please [sign up](https://www.twilio.com/try-twilio) for a free Twilio account. You will need your Account Sid and Auth Token available in your [Account Dashboard](https://www.twilio.com/user/account/). You will also need to create a [TwiML App](https://www.twilio.com/user/account/apps). Please make sure that you point the TwiML app's Voice Request URL to your web application's public URL, e.g.,  http://companyfoo.com/roulette.
You will also need a verified phone number to be used as caller ID when placing phone calls.  See the "Verified Caller Ids" section
under [Numbers](https://www.twilio.com/user/account/phone-numbers)
tab in your Twilio Account.

Deployment
---

In order to Twilio can communicate with your web application, it needs to be
deployed on the public Internet.  

Click the button below to automatically set up the app using your Heroku account. Please enter your Twilio Account Sid, Auth token, TwiML App Sid and verified phone number when prompted.

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

Testing
---

Please open http://your-heroku-web-app.com/ in your browser. 

You should see a message saying 'Welcome to Twilio!'


Client Configuration
---

In the urls.xml file of your Android Client simply add links to your newly created Heroku server like so

    <string name="app_capability_url">https://guarded-brook-6452.herokuapp.com/token</string>
    <string name="app_hangup_url">https://guarded-brook-6452.herokuapp.com/hangup</string>

Usage
---

At launch, the client connects to http://your-heroku-web-app.com/token to retrieve its capability token.

The app utilizes the Twilio Queue API to create a series of connections between different people. Everytime someone calls our server, we check if our queue is empty or not. If it's empty, we put the user into the queue. They will listen to a pre-recorded Mp3 while they wait to connect to someone. If the queue isn’t empty, the user is connected to whoever is in the queue, and the queue returns to being empty. If a user in a queue hangs up, our client app calls the /hangup endpoint which empties the queue. 

License
---

MIT license (Copyright 2015 Twilio inc.)