#!/usr/bin/python3
import sys
import spotipy
import spotipy.util as util
import pprint
import re

scope = 'user-library-modify,user-read-currently-playing,playlist-modify-private,playlist-modify-public,user-modify-playback-state'

#username = 'charlesgarza'
username = input('enter username: ')

token = util.prompt_for_user_token(username,scope,client_id='1724acae52b7454ebc82f298367ae287',
client_secret='437db12377ac45dc9a2da35bc997129e',redirect_uri='http://localhost/')

count = 0
i = 0
off = 0
allTracks = []
playlistName = "Custom Mood Playlist"
playlistIds = []
img = '/mnt/c/users/charl/desktop/personal/image.png'

#random playlist key
#key = '37i9dQZF1DXdPec7aLTmlC' #second playlist
key = '37i9dQZF1DX7KNKjOK0o75' #third playlist

if token:
    sp = spotipy.Spotify(auth=token)
    sp.trace = False 
 
    currTrack = sp.currently_playing()
    playlists = sp.category_playlists(category_id='mood', country='US', offset=off) 
    
    allUserPlaylists = sp.current_user_playlists()
    count = len(allUserPlaylists)
    
    while i < count - 1:
        playlistNames = allUserPlaylists['items'][i]['name'] 
        
        if (playlistName == playlistNames): 
            myPlaylistId = allUserPlaylists['items'][i]['id']
            sp.user_playlist_unfollow(username, myPlaylistId)
            print('Playlist exists... ' + playlistName)
            print('...Removing and creating a new playlist')
            newPlaylist = sp.user_playlist_create(username, playlistName, 
                                         description="Your Personalized Mood Playlist!")
             
            myPlaylistId = newPlaylist['id']            
            break
        elif (i == count - 2): 
            print('Does not exist...')
            print('...Creating a new playlist: ' + playlistName)
            newPlaylist = sp.user_playlist_create(username, playlistName, 
                                         description="Your Personalized Mood Playlist!")
            myPlaylistId = newPlaylist['id']
        
        i += 1

    i = 0 
    maxCount = 20
    
    while i < maxCount:
        try:
            currTrackName = playlists['playlists']['items'][i]['name']
            currTrackId = playlists['playlists']['items'][i]
            uri = currTrackId['uri'] 
            playlistIds.append(uri)
        except: 
            print('... Captured all playlists!')
            break
        
        i += 1
        
        if (i == maxCount):
            i = 0
            off += 20
            playlists = sp.category_playlists(category_id='mood', country='US', offset=off) 
    
    i = 0
    j = 0
    count = 0
    bool = 0

    for id in playlistIds:  
        val = sp.user_playlist('spotify', playlist_id=id) 
        
        length = len(val['tracks']['items'])
        id = re.sub(r'^\w*:[\w]+:', '', id)
        print('Keys: ' + id + " " + str(length))

        if (key == id): 
            while i < length: 
                if count == 99:
                    break
                allTracks.append(val['tracks']['items'][i]['track']['id'])
                
                i += 1
                count += 1
                bool = 1
        
            sp.user_playlist_add_tracks(username, myPlaylistId, allTracks)
            if bool == 1:
                print('...Found playlist!')
                print('...Exiting')
                break   
    
    #print out all track names from the spotify main playlists
    
    sys.stdout.write('\"[')
    length = len(trackNames)
    cnt = 0
    maxCnt = 0
    for tracks in allTrackInfo:
        if (cnt == 18):
            sys.stdout.write('\\\"'+tracks+'\\\"')
            sys.stdout.write(']\"')
            print('\n')
            sys.stdout.write('\"[')
            cnt = 0
        elif (maxCnt == length - 1):
            sys.stdout.write('\\\"'+tracks+'\\\"')
            break
        else:
            sys.stdout.write('\\\"'+tracks+'\\\",')
        cnt += 1
        maxCnt += 1
    sys.stdout.write(']\"')
