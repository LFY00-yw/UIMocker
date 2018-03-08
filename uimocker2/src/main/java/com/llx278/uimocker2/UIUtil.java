package com.llx278.uimocker2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * Created by llx on 02/01/2018.
 */

public class UIUtil {

    /**
     * Removes invisible Views.
     *
     * @param viewList an Iterable with Views that is being checked for invisible Views
     * @return a filtered Iterable with no invisible Views
     */

    public static <T extends View> ArrayList<T> removeInvisibleViews(Iterable<T> viewList) {
        ArrayList<T> tmpViewList = new ArrayList<T>();
        for (T view : viewList) {
            if (view != null && view.isShown()) {
                tmpViewList.add(view);
            }
        }
        return tmpViewList;
    }

    /**
     * Filters Views based on the given class type.
     *
     * @param classToFilterBy the class to filter
     * @param viewList the Iterable to filter from
     * @return an ArrayList with filtered views
     */

    public static <T> ArrayList<T> filterViews(Class<T> classToFilterBy, Iterable<?> viewList) {
        ArrayList<T> filteredViews = new ArrayList<T>();
        for (Object view : viewList) {
            if (view != null && classToFilterBy.isAssignableFrom(view.getClass())) {
                filteredViews.add(classToFilterBy.cast(view));
            }
        }
        viewList = null;
        return filteredViews;
    }

    /**
     * Filters all Views not within the given set.
     *
     * @param classSet contains all classes that are ok to pass the filter
     * @param viewList the Iterable to filter form
     * @return an ArrayList with filtered views
     */

    public  static ArrayList<View> filterViewsToSet(List<Class<? extends ViewGroup>> classSet, Iterable<View> viewList) {
        ArrayList<View> filteredViews = new ArrayList<>();
        for (View view : viewList) {
            if (view == null)
                continue;
            for (Class<?> filter : classSet) {
                if (filter.isAssignableFrom(view.getClass())) {
                    filteredViews.add(view);
                    break;
                }
            }
        }
        return filteredViews;
    }

    /**
     * Orders Views by their location on-screen.
     *
     * @param views The views to sort
     * @see ViewLocationComparator
     */

    public static void sortViewsByLocationOnScreen(List<? extends View> views) {
        Collections.sort(views, new ViewLocationComparator());
    }

    /**
     * Orders Views by their location on-screen.
     *
     * @param views The views to sort
     * @param yAxisFirst Whether the y-axis should be compared before the x-axis
     * @see ViewLocationComparator
     */

    public static void sortViewsByLocationOnScreen(List<? extends View> views, boolean yAxisFirst) {
        Collections.sort(views, new ViewLocationComparator(yAxisFirst));
    }

    /**
     * Checks if a View matches a certain string
     *
     * @param regex the regex to match
     * @param view the view to check
     * @return true if matches
     */

    public static boolean textViewOfMatches(String regex, TextView view){

        Pattern pattern = null;
        try{
            pattern = Pattern.compile(regex);
        }catch(PatternSyntaxException e){
            pattern = Pattern.compile(regex, Pattern.LITERAL);
        }
        String textStr = "";
        if (view.getText() != null){
            textStr = view.getText().toString();
        }
        Matcher matcher = pattern.matcher(textStr);

        if (matcher.find()){
            return true;
        }

        if (view.getError() != null){
            matcher = pattern.matcher(view.getError().toString());
            if (matcher.find()){
                return true;
            }
        }

        if (textStr.equals("") && view.getHint() != null){
            matcher = pattern.matcher(view.getHint().toString());
            if (matcher.find()){
                return true;
            }
        }
        return false;
    }

    public static boolean textOfMatches(String regex,String text) {
        Pattern pattern = null;
        try{
            pattern = Pattern.compile(regex);
        }catch(PatternSyntaxException e){
            pattern = Pattern.compile(regex, Pattern.LITERAL);
        }
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * Filters a collection of Views and returns a list that contains only Views
     * with text that matches a specified regular expression.
     *
     * @param views The collection of views to scan
     * @param regex The text pattern to search for
     * @return A list of views whose text matches the given regex
     */

    public static <T extends TextView> List<T> filterViewsByText(Iterable<T> views, String regex) {
        return filterViewsByText(views, Pattern.compile(regex));
    }

    /**
     * Filters a collection of Views and returns a list that contains only Views
     * with text that matches a specified regular expression.
     *
     * @param views The collection of views to scan
     * @param regex The text pattern to search for
     * @return A list of views whose text matches the given regex
     */

    public static <T extends TextView> List<T> filterViewsByText(Iterable<T> views, Pattern regex) {
        final ArrayList<T> filteredViews = new ArrayList<T>();
        for (T view : views) {
            if (view != null && regex.matcher(view.getText()).matches()) {
                filteredViews.add(view);
            }
        }
        return filteredViews;
    }
}
