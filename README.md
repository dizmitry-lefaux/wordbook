#### TODO:

* bottom bar:
  * update icons
* sessions:
  * make size configurable
  * change animated card design
  * add 'Session' screen functionality
  * move 'Session' composable functions to separate files
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

#### ISSUES:
* Pills:
  * work on pills/content background
* 'Words' screen:
  * workaround to recompose input fields after removal
* 'Words' screen:
  * prevent save word without selected source (e.g. prevent open dropdown or show error)
  * clear translation value on submit
