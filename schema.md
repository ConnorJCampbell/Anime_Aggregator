# CIS*4450 Project - Anime Aggregator Schema

## Anime Object Variables
All data has been gathered from [MyAnimeList](https://myanimelist.net).

| Name        | Type   | Purpose                                                                                               |
|-------------|--------|-------------------------------------------------------------------------------------------------------|
| title       | text   | The title of the anime. Must be unique.                                                               |
| type        | text   | Format of the anime, eg "TV", "Movie", "OVA"                                                          |
| episodes    | int    | Number of episodes                                                                                    |
| status      | text   | Indicates whether or not the anime is currently airing.                                               |
| startDate   | text   | The start date of the anime                                                                           |
| endDate     | text   | The end date of the anime                                                                             |
| season      | text   | The season the anime first aired, eg "Winter" or "Summer"                                             |
| broadcast   | text   | The network where the anime initally aired.                                                           |
| producer    | text   | Producers of the anime, usually muliplte entities                                                     |
| licensor    | text   | Company that provides a legal means of accessing the anime to the public                              |
| studio      | text   | The people/company that make the anime                                                                |
| source      | text   | Where the anime was adapted from, eg "Manga", "Light Novel", "Original"                               |
| genre       | text   | Genre of the anime, eg "Fantasy" or "Shounen". One anime can have multiple genres seperated by commas |
| duration    | text   | Length of each episode                                                                                |
| score       | double | Average score of the anime out of 10                                                                  |
| scoredBy    | int    | Number of users who rated the anime                                                                   |
| members     | int    | Number of users who watched the anime                                                                 |
| favorites   | int    | Number of users that added the anime to their "favourites"                                            |
| description | text   | A long text description of the anime                                                                  |

I also used my SoCS mySQL Database to create a table that matches the structure of my anime object. This table stores
all the data for anime