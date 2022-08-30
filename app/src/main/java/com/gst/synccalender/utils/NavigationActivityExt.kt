package com.gst.synccalender.utils



import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.gst.synccalender.R

/**
 * Navigate with safety to a destination from the current navigation graph. This supports both navigating
 * via an {@link NavDestination#getAction(int) action} and directly navigating to a destination.
 *
 * @param resId an {@link NavDestination#getAction(int) action} id or a destination id to
 *              navigate to
 * @param args arguments to pass to the destination
 * @param navOptions special options for this navigation operation
 * @param navigatorExtras extras to pass to the Navigator
 */

/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

fun NavController.navigateSafe(
    @IdRes action: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): Boolean {
    return try {
        navigate(action, args, navOptions, navigatorExtras)
        true
    } catch (t: Throwable) {

        false
    }
}

/**
 * Navigate with safety via the given {@link NavDirections}
 *
 * @param directions directions that describe this navigation operation
 * @param navOptions special options for this navigation operation
 */
fun NavController.navigateSafe(
    directions: NavDirections,
    navOptions: NavOptions? = null
): Boolean {
    return try {
        navigate(directions, navOptions)
        true
    } catch (t: Throwable) {
        false
    }
}

/**
 * Navigate with safety via the given {@link NavDirections}
 *
 * @param directions directions that describe this navigation operation
 * @param navigatorExtras extras to pass to the {@link Navigator}
 */
fun NavController.navigateSafe(
    directions: NavDirections,
    navigatorExtras: Navigator.Extras
): Boolean {
    return try {
        navigate(directions, navigatorExtras)
        true
    } catch (t: Throwable) {
        false
    }
}

/**
 * Navigate with safety to a destination via the given deep link {@link Uri}.
 * {@link NavDestination#hasDeepLink(Uri)} should be called on
 * {@link #getGraph() the navigation graph} prior to calling this method to check if the deep
 * link is valid. If an invalid deep link is given, an {@link IllegalArgumentException} will be
 * thrown.
 *
 * @param deepLink deepLink to the destination reachable from the current NavGraph
 * @param navOptions special options for this navigation operation
 * @param navigatorExtras extras to pass to the Navigator
 * @see #navigate(NavDeepLinkRequest, NavOptions, Navigator.Extras)
 */
fun NavController.navigateSafe(
    deepLink: Uri,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
): Boolean {
    return try {
        navigate(deepLink, navOptions, navigatorExtras)
        true
    } catch (t: Throwable) {
        false
    }
}

fun CoreActivity<*>.animationCloseActivity() {
    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right)
}

fun CoreActivity<*>.animationOpenActivity() {
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
}

fun CoreFragment<*>.animationCloseActivity() {
    activity?.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right)
}

fun CoreFragment<*>.animationOpenActivity() {
    activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
}