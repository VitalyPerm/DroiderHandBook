package com.elvitalya.droiderhandbook.ui.main

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    globalViewModel: GlobalViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var reloadQuestionsAlertDialogState by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = getTopAppBarTitle(
                        currentDestination?.route
                    )
                ), modifier = Modifier
                    .clickable(enabled = currentDestination?.route == BottomNavigationScreen.Sections.route) {
                        navController.navigate(Destinations.ColorsScreen.route)
                    }, color = MaterialTheme.colorScheme.onTertiary
            )
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ), actions = {
            AnimatedVisibility(currentDestination?.route == BottomNavigationScreen.Sections.route) {
                IconButton(onClick = {
                    reloadQuestionsAlertDialogState = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        null,
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        })
    }, bottomBar = {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.tertiary,
        ) {
            bottomNavigationItems.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                val iconTintColor by animateColorAsState(targetValue = if (selected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface)
                BottomNavigationItem(icon = {
                    val image = when (screen) {
                        BottomNavigationScreen.Favorite -> Icons.Filled.Favorite
                        BottomNavigationScreen.Search -> Icons.Filled.Search
                        BottomNavigationScreen.Sections -> Icons.Filled.List
                        BottomNavigationScreen.Test -> Icons.Filled.Book
                    }
                    Icon(
                        imageVector = image, contentDescription = null, tint = iconTintColor
                    )
                }, selected = selected, onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
            }
        }
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavigationScreen.Sections.route,
            Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            composable(BottomNavigationScreen.Sections.route) { SectionsScreen(navController) }
            composable(BottomNavigationScreen.Favorite.route) { FavoriteScreen(navController) }
            composable(BottomNavigationScreen.Search.route) { SearchScreen(navController) }
            composable(BottomNavigationScreen.Test.route) { TestScreen(navController) }
            composable(Destinations.ColorsScreen.route) { ColorsScreen() }
            composable(
                route = Destinations.QuestionDetail.route,
                arguments = listOf(
                    navArgument(
                        name = NavConstants.QUESTION_ID,
                        builder = { type = NavType.IntType })
                )
            ) {
                QuestionDetailsScreen(id = it.arguments?.getInt(NavConstants.QUESTION_ID) ?: 0)
            }
        }
    }

    ReloadQuestionsAlertDialog(state = reloadQuestionsAlertDialogState, onYesClick = {
        reloadQuestionsAlertDialogState = false
        globalViewModel.reloadQuestions()
    }, onNoClick = { reloadQuestionsAlertDialogState = false })
}

private val bottomNavigationItems = listOf(
    BottomNavigationScreen.Sections,
    BottomNavigationScreen.Favorite,
    BottomNavigationScreen.Search,
    BottomNavigationScreen.Test
)


private fun getTopAppBarTitle(route: String?): Int = when (route) {
    BottomNavigationScreen.Favorite.route -> BottomNavigationScreen.Favorite.resourceId
    BottomNavigationScreen.Search.route -> BottomNavigationScreen.Search.resourceId
    BottomNavigationScreen.Sections.route -> BottomNavigationScreen.Sections.resourceId
    BottomNavigationScreen.Test.route -> BottomNavigationScreen.Test.resourceId
    Destinations.QuestionDetail.route -> Destinations.QuestionDetail.resourceId
    Destinations.ColorsScreen.route -> Destinations.ColorsScreen.resourceId
    else -> R.string.empty_string
}

 */