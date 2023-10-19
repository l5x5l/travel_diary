package com.strayalphaca.travel_diary

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface RootDestinations {
    val route : String
}

object Start : RootDestinations {
    override val route: String = "start"
}

object HomeGraph : RootDestinations {
    override val route: String = "home_graph"
}

object SettingsGraph : RootDestinations {
    override val route: String = "settings_graph"
}

object LoginGraph : RootDestinations {
    override val route: String = "login_graph"
}

object DiaryDetail : RootDestinations {
    override val route: String = "diary_detail"
    const val diaryId = "diary_id"
    val routeWithArgs = "${route}/{$diaryId}"
    val arguments = listOf(navArgument(diaryId){type = NavType.StringType})
    val deepLinks = listOf(navDeepLink { uriPattern = "traily://${route}/{$diaryId}" })
}

object DiaryWrite : RootDestinations {
    override val route: String = "diary_write"
    const val diaryId = "diary_id"
    const val diaryDateString = "diary_date"
    val routeWithArgs = "${route}/{${diaryId}}&{${diaryDateString}}"
    val arguments = listOf(
        navArgument(diaryId){type = NavType.StringType},
        navArgument(diaryDateString){type = NavType.StringType}
    )
    val deepLinks = listOf(navDeepLink { uriPattern = "traily://${route}/{$diaryId}&{$diaryDateString}" })
}

object Video : RootDestinations {
    override val route: String = "video"
    const val videoUri = "video_uri"
    val routeWithArgs = "${route}?{$videoUri}"
    val arguments = listOf(navArgument(videoUri){type = NavType.StringType})
    val deepLinks = listOf(navDeepLink { uriPattern = "traily://${route}?{$videoUri}" })
}

object Intro : RootDestinations {
    override val route: String = "intro"
}

object Lock : RootDestinations {
    override val route: String = "lock"
}

object DiaryList : RootDestinations {
    override val route: String = "diary_list"
    const val cityGroupId = "city_group_id"
    val routeWithArgs = "${route}/{${cityGroupId}}"
    val arguments = listOf(navArgument(cityGroupId){type = NavType.IntType})
    val deepLinks = listOf(navDeepLink { uriPattern = "traily://${route}/{${cityGroupId}}" })
}
