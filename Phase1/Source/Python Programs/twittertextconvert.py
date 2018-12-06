# Import the necessary package to process data in JSON format
try:
    import json
except ImportError:
    import simplejson as json

# We use the file saved from last step as example
tweets_filename = 'twitter_output.txt'
tweets_file = open("Downloads/tweets_out.json", "r")
tweets_text = [] # We will store the text of every tweet in this list

target = open("FileOutput.txt", 'w')

for line in tweets_file:
    try:
        # Read in one line of the file, convert it into a json object 
        tweet = json.loads(line.strip())
        if 'text' in tweet: # only messages contains 'text' field is a tweet
            target.truncate()
            target.write( tweet['text'] )
            #print(tweet['text'])
    except:
        # read in a line is not in JSON format (sometimes error occured)
        continue
target.close()
