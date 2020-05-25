
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private int ITEM_TO_TEST=4;
    private Neighbour NEIGHBOUR_TO_TEST= new DummyNeighbourApiService().getNeighbours().get(ITEM_TO_TEST);

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }


    @Test
    public void isDetailPage() {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(actionOnItemAtPosition(ITEM_TO_TEST, click()));
        onView(withId(R.id.neighbour_picture)).check(matches(isDisplayed()));
    }

    @Test
    public void matchNameTest() {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(actionOnItemAtPosition(ITEM_TO_TEST, click()));
        onView(withId(R.id.textView_neighbour_name)).check(matches(withText(NEIGHBOUR_TO_TEST.getName())));
    }
    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(actionOnItemAtPosition(ITEM_TO_TEST, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void testFavourites() {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(actionOnItemAtPosition(ITEM_TO_TEST, click()));
        onView(withId(R.id.button_Favorite)).perform(click());
        onView(withId(R.id.back_button)).perform(click());

        onView(allOf(withContentDescription("Favorites"), isDisplayed())).perform(click());


        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(1));
        onView(allOf(withId(R.id.list_neighbours),isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.button_Favorite)).perform(click());
        onView(withId(R.id.back_button)).perform(click());

        onView(allOf(withContentDescription("Favorites"),isDisplayed())).perform(click());

        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).check(withItemCount(0));




    }
}