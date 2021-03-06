# WeatherNotice
---
Tyler Hitchner, Adam Holt, Oliver Lu

## 1. Introduction
Ever need to know if you need to put the pool cover on so it is not too cold to swim later on or when alert to bring the plants if it's too cold outside. With WeatherNotice, it will solve those issues for you.  

-	Record locations where you like to be notified of weather changes.
-	Stay ahead of location and you are going to be better prepared.
-	Alert you if action is needed, taking plants in, turn sub pump on, close sunroof ect.
 
 ## 2. Storyboard
 
 
Link: [Storyboard](https://projects.invisionapp.com/prototype/ckkbtob2p00efe701f83r6ter/play)

![image1](https://github.com/OliverPo666/image-for-weather-notice/blob/main/1.png)
   
## 3. Functional Requirements

#### Examples
1.1  
**Given** feed of weather data  <br />
**When** the location is lower 70 degree  <br />
**Then** I should receive a push notification to cover the pool: 
- Message: Cover pool with pool cover
- Reason: Current temperature is lower then set parameters

1.2  
**Given** feed of weather data  <br />
**When** future temperature is lower then what the set parameters are <br />
**Then** I should receive a push notification to cover the plants outside 

- Message: Cover plants to keep them alive
- Reason: future temperature is lower then set parameters

1.3 <br />
**Given** feed of weather data  <br />
**When** I search for the zip code for 45236 <br />
**Then** Then I should receive the weather data

- Temperature: 70 degree
- Weather Type: Sunny
- precipitation : 45%
- Humidity: 67%
- Wind: 14mph
- City: Cincinnati
- State: Ohio
- Country: USA

1.4 <br /> 
**Given** the weather data  <br />
**When** I search for the zip code for "5htdj" ( not a real zip code)  <br />
**Then** I should receive no results

- Use of phone GPS to know user locations
- Notification to user when weather is outside user requirements 
- Up to date weather status of user set Location

### Requirement 101: Get weather forecast and alerts for desired locations

#### Scenario
As a user who wants or needs to track the weather in different locations or their own.
Users can be informed of ever changing weather and to alert me if a certain location are experience undesirable weather.

- it's going to be cool and may want to put the pool cover up to keep it warm for later use.
- when snowing to alert me I need to start leave early for work to clean off the snow and prepare for unwanted road conditions.
- Then alert me the it is freezing and I need take in the plants inside to keep alive


## 4. Class Diagram

![WeatherNoticeUML](https://user-images.githubusercontent.com/60677383/107177640-4946e980-69a0-11eb-93f9-2933a4698860.png)


## 5. Class Diagram Description
- Main start off with use of phone GPS to know user current location.
- Api need be able to get weather api data.
- Database store user information for each location as well was the type of weather and or temperatures to look out for.
- Logic figure out if a notification need to be sent out with display message the user stored to notify them of what currently happening. 

## 6. A Product Backlog

Product the main goal and any extra functional to include, that are durable for this semester
More in the https://github.com/Hitchnt/It3048c/projects/2

## 7. A scrum or kanban board, using GitHub projects (preferred), Trello, Scrumy.com, or something similar, that contains:
https://github.com/Hitchnt/It3048c/projects/2

## 8.Scrum Roles

- DevOps/Product Owner/Scrum Master: T.Hitchner
- Frontend Developer: Oliver Lu 
- Integration Developer: Adam Holt


## 9. Link to WebEx for your 8:00 Sunday group stand up.  If you choose a different tool and/or different time, that's fine, just indicate it in the document.


We are using Discord weekly for our standup.

