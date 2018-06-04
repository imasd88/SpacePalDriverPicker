package com.spacepal.internal.app.model.realm

import android.app.Application

class RealmController(application: Application) {
//    val realm: Realm
//
//    val orders: List<AssignmentItem>
//        get() = realm.copyFromRealm<E>(realm.where(AssignmentItem::class.java).findAll())
//
//    val roles: List<Role>
//        get() = realm.copyFromRealm<E>(realm.where(Role::class.java!!).findAll())
//
//    init {
//        realm = Realm.getDefaultInstance()
//    }
//
//    /*********** Saving object must be RealmObject  */
//    fun save(mLocation: Any) {
//        realm.beginTransaction()
//        realm.copyToRealm(mLocation as RealmObject)
//        realm.commitTransaction()
//    }
//
//
//    fun saveRoles(roleList: List<Role>) {
//        realm.executeTransaction { realm ->
//            realm.where(Role::class.java!!).findAll().deleteAllFromRealm()
//            realm.insert(roleList)
//        }
//
//    }
//
//    fun saveOrders(assignmentItemList: List<AssignmentItem>) {
//        realm.executeTransaction { realm ->
//            realm.where(AssignmentItem::class.java!!).findAll().deleteAllFromRealm()
//            realm.copyToRealm(assignmentItemList)
//        }
//
//    }
//
//
//    //Refresh the realm istance
//    fun refresh() {
//
//        realm.refresh()
//    }
//
//    companion object {
//
//        var instance: RealmController? = null
//            private set
//
//        fun with(fragment: Fragment): RealmController {
//
//            if (instance == null) {
//                instance = RealmController(fragment.activity!!.application)
//            }
//            return instance
//        }
//
//        fun with(activity: Activity): RealmController {
//
//            if (instance == null) {
//                instance = RealmController(activity.application)
//            }
//            return instance
//        }
//
//        fun with(application: Application): RealmController {
//
//            if (instance == null) {
//                instance = RealmController(application)
//            }
//            return instance
//        }
//    }
//

}