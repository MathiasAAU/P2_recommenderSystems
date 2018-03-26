import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Attr;


public class Parse {

     public static void main(String args[]){
          System.out.println("running...");

          ArrayList<Document> recDoc = new ArrayList<>();
          ArrayList<Document> revDoc = new ArrayList<>();

          Document catDoc;


          try {

              //Get Document object after parsing the html from given url.
              catDoc = Jsoup.connect(
                  "https://www.allrecipes.com/recipes/233/world-cuisine/asian/indian/?page=2").get(); //Connects to category "Indian" page 1

              String title = catDoc.title(); //Get title of link
              System.out.println("  Title: " + title); //Print title

              Elements recipesURL = catDoc.select(
                  ".fixed-recipe-card__title-link[href]"); //Extracts class containing a link in each recipe box


              for (int i = 0; i < recipesURL.size(); i++) {

                  System.out.println("URL" + (i + 1) + ": " + recipesURL.get(i).text()); //Prints title of link of a recipe
                  System.out.println(recipesURL.get(i).attr("abs:href")); //Prints link to recipe
                  recDoc.add(Jsoup.connect(recipesURL.get(i).attr("abs:href")).get()); //Connects to a recipe link

                  Recipe newRecipe = new Recipe(recipesURL.get(i).text()); //Recipe name

                  newRecipe.setLink(recipesURL.get(i).attr("abs:href")); //Link to recipe

                  newRecipe.setSubmitter(recDoc.get(i).select(".submitter__name").text()); //Recipe submitter

                  newRecipe.setDescription(recDoc.get(i).select(".submitter__description").text()); //Recipe description

                  /* Directions */
                  Elements direction = recDoc.get(i).select(
                      ".recipe-directions__list li.step span.recipe-directions__list--item"); //Recipe directions

                  for (int j = 0; j < direction.size() - 1; j++) {
                      newRecipe.directions.add(direction.get(j).text()); //Stores recipe directions in order
                  }

                  /* Prep and cook time */
                  Elements cookTime = recDoc.get(i).select(".prepTime__item time[datetime]");

                  for (int k = 0; k < cookTime.size(); k++) {
                      newRecipe.cookTime.add(cookTime.get(k).attr("datetime"));
                      System.out.println(newRecipe.cookTime.get(k));
                  }

                  /* Servings */
                  Elements servings = recDoc.get(i).select(
                   ".recipe-ingredients__header__toggles meta[itemprop=recipeYield]");
                  newRecipe.setServings(servings.get(0).attr("content"));
                  System.out.println(newRecipe.getServings());

                  /* Calories */
                  newRecipe.setCalories(recDoc.get(i).select(".calorie-count").text());
                  System.out.println(newRecipe.getCalories());

                  /* Reviews */
                  Elements reviewsURL = recDoc.get(i).select(
                      ".review-container.clearfix div.review-detail a.review-detail__link[href]"); //Extracts full review links

                  revDoc.add(Jsoup.connect(reviewsURL.get(i).attr("abs:href")).get()); //Connects to review link

                  Elements reviews = revDoc.get(i).select(
                      ".review-container div.review-detail.clearfix p"); //Extracts text in full review

                  System.out.println(reviews.get(0).ownText());

                  /* Rating */
                  Elements rating = recDoc.get(i).select(
                      ".recipe-summary.clearfix div.recipe-summary__stars " +
                          "span.aggregate-rating meta[itemprop=ratingValue]"); //Extracts class containing recipe rating
                  newRecipe.setRating(rating.get(0).attr("content"));


                  /* Separate recipes in printing and wait a second */
                  System.out.println();
                  TimeUnit.MILLISECONDS.sleep(1000);

              }


              /* Prints stack trace if exceptions */
              } catch(IOException | InterruptedException e) {
              e.printStackTrace();
          }

          System.out.println("done");
     }

}
