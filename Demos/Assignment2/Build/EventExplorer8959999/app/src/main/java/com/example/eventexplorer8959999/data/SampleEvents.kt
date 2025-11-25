package com.example.eventexplorer8959999.data

import com.example.eventexplorer8959999.R

// Sample data for events
val sampleEvents = listOf(
    // Music Events
    Event(
        name = "Jazz Under the Stars",
        imageRes = R.drawable.music_event,
        date = "2024-12-15",
        time = "20:00",
        location = "Kitchener",
        description = "An enchanting evening of smooth jazz featuring local and international artists under the open sky.",
        ticketPrice = 45.00f,
        category = EventCategories.Music
    ),
    Event(
        name = "Rock Revolution Festival",
        imageRes = R.drawable.music_event,
        date = "2024-12-20",
        time = "18:00",
        location = "Waterloo",
        description = "Three stages of non-stop rock music featuring headline acts and emerging bands.",
        ticketPrice = 75.00f,
        category = EventCategories.Music
    ),
    Event(
        name = "Classical Symphony Night",
        imageRes = R.drawable.music_event,
        date = "2024-12-10",
        time = "19:30",
        location = "Toronto",
        description = "Experience Beethoven's greatest works performed by the City Philharmonic Orchestra.",
        ticketPrice = 60.00f,
        category = EventCategories.Music
    ),
    Event(
        name = "Electronic Beats Festival",
        imageRes = R.drawable.music_event,
        date = "2024-12-28",
        time = "22:00",
        location = "Kitchener",
        description = "Dance all night to the best DJs in electronic music with stunning visual effects.",
        ticketPrice = 55.00f,
        category = EventCategories.Music
    ),
    Event(
        name = "Acoustic Unplugged Sessions",
        imageRes = R.drawable.music_event,
        date = "2024-12-05",
        time = "19:00",
        location = "Waterloo",
        description = "Intimate acoustic performances in a warm, candlelit atmosphere with local singer-songwriters.",
        ticketPrice = 25.00f,
        category = EventCategories.Music
    ),
    Event(
        name = "Hip Hop Block Party",
        imageRes = R.drawable.music_event,
        date = "2024-12-22",
        time = "17:00",
        location = "Toronto",
        description = "Street culture celebration featuring live rap battles, breakdancing, and graffiti art.",
        ticketPrice = 30.00f,
        category = EventCategories.Music
    ),

    // Sports Events
    Event(
        name = "City Derby Football Match",
        imageRes = R.drawable.sport_event,
        date = "2024-12-12",
        time = "15:00",
        location = "Kitchener",
        description = "The biggest rivalry match of the season between the city's top two football clubs.",
        ticketPrice = 50.00f,
        category = EventCategories.Sports
    ),
    Event(
        name = "Championship Basketball Finals",
        imageRes = R.drawable.sport_event,
        date = "2024-12-18",
        time = "19:00",
        location = "Waterloo",
        description = "Watch the thrilling conclusion to this season's basketball championship series.",
        ticketPrice = 65.00f,
        category = EventCategories.Sports
    ),
    Event(
        name = "Marathon City Run",
        imageRes = R.drawable.sport_event,
        date = "2024-12-08",
        time = "07:00",
        location = "Toronto",
        description = "Join thousands of runners in the annual city marathon with 5K, 10K, and full marathon options.",
        ticketPrice = 35.00f,
        category = EventCategories.Sports
    ),
    Event(
        name = "Tennis Open Tournament",
        imageRes = R.drawable.sport_event,
        date = "2024-12-14",
        time = "11:00",
        location = "Kitchener",
        description = "World-class tennis action featuring top-ranked players competing for the championship title.",
        ticketPrice = 80.00f,
        category = EventCategories.Sports
    ),
    Event(
        name = "Boxing Championship Night",
        imageRes = R.drawable.sport_event,
        date = "2024-12-30",
        time = "20:00",
        location = "Waterloo",
        description = "Witness epic heavyweight championship bouts with rising stars and veteran fighters.",
        ticketPrice = 95.00f,
        category = EventCategories.Sports
    ),
    Event(
        name = "Swimming Gala Finals",
        imageRes = R.drawable.sport_event,
        date = "2024-12-06",
        time = "18:00",
        location = "Toronto",
        description = "Olympic-style swimming competition showcasing the fastest swimmers in the region.",
        ticketPrice = 40.00f,
        category = EventCategories.Sports
    ),

    // Workshop Events
    Event(
        name = "Digital Photography Masterclass",
        imageRes = R.drawable.workshop_event,
        date = "2024-12-11",
        time = "10:00",
        location = "Kitchener",
        description = "Learn advanced photography techniques, lighting, and post-processing from professional photographers.",
        ticketPrice = 85.00f,
        category = EventCategories.Workshop
    ),
    Event(
        name = "Pottery and Ceramics Workshop",
        imageRes = R.drawable.workshop_event,
        date = "2024-12-16",
        time = "14:00",
        location = "Waterloo",
        description = "Hands-on pottery workshop where you'll create your own ceramic pieces from scratch.",
        ticketPrice = 70.00f,
        category = EventCategories.Workshop
    ),
    Event(
        name = "Culinary Arts: French Cuisine",
        imageRes = R.drawable.workshop_event,
        date = "2024-12-19",
        time = "17:00",
        location = "Toronto",
        description = "Master the art of French cooking with a Michelin-starred chef teaching classic techniques.",
        ticketPrice = 120.00f,
        category = EventCategories.Workshop
    ),
    Event(
        name = "Web Development Bootcamp",
        imageRes = R.drawable.workshop_event,
        date = "2024-12-09",
        time = "09:00",
        location = "Kitchener",
        description = "Intensive day-long workshop covering HTML, CSS, and JavaScript fundamentals for beginners.",
        ticketPrice = 95.00f,
        category = EventCategories.Workshop
    ),
    Event(
        name = "Watercolor Painting Class",
        imageRes = R.drawable.workshop_event,
        date = "2024-12-23",
        time = "13:00",
        location = "Waterloo",
        description = "Explore watercolor techniques and create beautiful landscape paintings in a supportive environment.",
        ticketPrice = 55.00f,
        category = EventCategories.Workshop
    ),
    Event(
        name = "Sustainable Gardening Workshop",
        imageRes = R.drawable.workshop_event,
        date = "2024-12-27",
        time = "10:00",
        location = "Toronto",
        description = "Learn organic gardening methods, composting, and how to grow your own vegetables sustainably.",
        ticketPrice = 45.00f,
        category = EventCategories.Workshop
    ),

    // Exhibition Events
    Event(
        name = "Modern Art Retrospective",
        imageRes = R.drawable.exhibition_event,
        date = "2024-12-07",
        time = "10:00",
        location = "Kitchener",
        description = "A comprehensive exhibition showcasing 100 years of modern art movements and masterpieces.",
        ticketPrice = 20.00f,
        category = EventCategories.Exhibition
    ),
    Event(
        name = "Ancient Civilizations Gallery",
        imageRes = R.drawable.exhibition_event,
        date = "2024-12-13",
        time = "09:00",
        location = "Waterloo",
        description = "Discover artifacts and treasures from ancient Egypt, Greece, and Rome in this fascinating exhibition.",
        ticketPrice = 25.00f,
        category = EventCategories.Exhibition
    ),
    Event(
        name = "Contemporary Photography Showcase",
        imageRes = R.drawable.exhibition_event,
        date = "2024-12-17",
        time = "11:00",
        location = "Toronto",
        description = "Stunning photographic works from award-winning contemporary photographers around the world.",
        ticketPrice = 15.00f,
        category = EventCategories.Exhibition
    ),
    Event(
        name = "Science and Innovation Expo",
        imageRes = R.drawable.exhibition_event,
        date = "2024-12-21",
        time = "10:00",
        location = "Kitchener",
        description = "Interactive exhibition featuring cutting-edge technology, robotics, and scientific discoveries.",
        ticketPrice = 30.00f,
        category = EventCategories.Exhibition
    ),
    Event(
        name = "Wildlife Photography Awards",
        imageRes = R.drawable.exhibition_event,
        date = "2024-12-26",
        time = "10:00",
        location = "Waterloo",
        description = "Breathtaking images of wildlife from around the globe, celebrating nature's beauty and diversity.",
        ticketPrice = 18.00f,
        category = EventCategories.Exhibition
    ),
    Event(
        name = "Sculpture Garden Opening",
        imageRes = R.drawable.exhibition_event,
        date = "2024-12-29",
        time = "12:00",
        location = "Toronto",
        description = "New outdoor exhibition featuring contemporary sculptures by renowned artists set in beautiful gardens.",
        ticketPrice = 22.00f,
        category = EventCategories.Exhibition
    )
)