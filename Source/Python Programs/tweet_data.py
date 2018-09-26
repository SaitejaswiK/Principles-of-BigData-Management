from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream

#Variables that contains the user credentials to access Twitter API 
ACCESS_TOKEN = "779311765163171844-RCUoOhu2R53ugDk3O8xTX50rgi2zj4o"
ACCESS_SECRET = "y9Evdnwz1tfI43fIyun18OQOxgt6HQjWh6g3Gb99ExwOI"
CONSUMER_KEY = "xMJiyum9ZLKuGeZDPl1uL3qeU"
CONSUMER_SECRET = "6df8h8k2O7AwBJgYREWwTfwB1MFXVBuUm4PttByrGiRKDj6bI5"

class StdOutListener(StreamListener):

    def on_data(self, data):
        print (data)
        return True

    def on_error(self, status):
        print (status)

if __name__ == '__main__':
    #This handles Twitter authetification and the connection to Twitter Streaming API
    l = StdOutListener()
    auth = OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
    auth.set_access_token(ACCESS_TOKEN, ACCESS_SECRET)
    stream = Stream(auth, l)

    #This line filter Twitter Streams to capture data by the keywords: 'python', 'javascript', 'ruby'
stream.filter(track=['python', 'javascript', 'ruby'])



