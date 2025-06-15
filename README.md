#### TODO:

* SessionViewModel methods refactoring
* update CardWithAnimatedSizeComponent to use new Word entity
* bottom bar:
  * update icons
* sessions:
  * make size configurable
  * change animated card design
  * convert 'Create session' actions to atomic transaction
  * implement weight dependent probability mechanism for already picked words
* books:
  * display source languages
* Buttons: change design (preliminary rectangular with borders)
* add 'Properties' screen
* add validations for multiple translations inputs
* warning on delete actions: language, source
* messages on adding items: language, source, word
* messages on delete items: language, source, word
* add non-latin languages support
* move strings to resources
* make languages sortable or movable
* make sources sortable or movable
* make 'Edit word' screen translations removals revertable on 'Cancel' click
* close 'Edit' popups with smooth recompose (close popup -> compose prev page)
* think about DI:
  * hilt (https://github.com/XRayAdamo/HiltExample)
  * koin (https://github.com/marelso/di_with_koin)
* https://www.reddit.com/r/androiddev/comments/1ecklee/using_multiple_view_models_for_different/
* https://github.com/Prashant-Chandel/Jetpack-compose-MVVM-Clean_Architect-Example-with-koin/blob/Developer/README.md
* load animations
* add data import from files
* add data import from claud vaults
* add multiple profiles

#### ISSUES:
* Pills:
  * work on pills/content background
* 'Words' screen:
  * workaround to recompose input fields after removal
* 'Sessions' screen:
  * Manage pill: implement multiple sources for session
  * Session pill: update session with source words when add words to session source
