import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Parse {

    public static void main(String args[]){
          System.out.println("running...");

          ArrayList<Document> documents = new ArrayList<>();
          ArrayList<String> recipeNames = new ArrayList<>();
          ArrayList<Elements> ratings = new ArrayList<>();
          ArrayList<String> descriptions = new ArrayList<>();
          ArrayList<String> prepTime = new ArrayList<>();
          ArrayList<String> numberOfServings = new ArrayList<>();
          ArrayList<String> caloriesInRecipe = new ArrayList<>();
          ArrayList<String> cookTime = new ArrayList<>();
          ArrayList<String> preptimeA = new ArrayList<>();
          ArrayList<String> arrServings = new ArrayList<>();

          Document catIndian;

          try {

            //Get Document object after parsing the html from given url.
            catIndian = Jsoup.connect("https://www.allrecipes.com/recipes/233/world-cuisine/asian/indian/?page=1").get(); //Connects to category "Indian" page 1

            String title = catIndian.title(); //Get title of link
            System.out.println("  Title: " + title); //Print title

            Elements URL = catIndian.select(".fixed-recipe-card__title-link[href]"); //Extracts class containing a link in each recipe box

            for (int i=0; i < URL.size(); i++) {
                System.out.println("URL" + (i+1) + ": " + URL.get(i).text()); //Prints title of link in a recipe's class
                System.out.println(URL.get(i).attr("abs:href")); //Prints link to recipe
                documents.add(Jsoup.connect(URL.get(i).attr("abs:href")).get());
                recipeNames.add(documents.get(i).select(".recipe-summary__h1").text());
                ratings.add(documents.get(i).select("data-ratingstars"));
                System.out.println(recipeNames + " " + ratings.get(i));
                descriptions.add(documents.get(i).select(".submitter__description").text());
                System.out.println(descriptions);

                /* ****VIRKER IKKE********
                prepTime.add(documents.get(i).select(".prepTime_item--time").text());
                System.out.println(prepTime);
                System.out.println(numberOfServings);
                */

                //Elements preptime = documents.get(i).select(".servings-count span.aggregate-rating:ratingValue");
                //preptimeA.add(preptime.get(i).attr("content"));

                //  cookTime.add(URL.contains("PT"));

               // cookTime.add(documents.get(i).select("div.directions--section__steps").text());
                //System.out.println(cookTime);

                //System.out.println(revDoc.get(i).select(".review-container div.review-detail.clearfix p"));

                //cookTime.add(documents.get(i).select("span.prepTime__item--time").text());
                //System.out.println(cookTime);
                //System.out.println(documents.get(i).select("div.directions--sections__steps time"));

                Elements demtimes = documents.get(i).select(".prepTime__item time[datetime]");

                for (int k=0; k < demtimes.size(); k++)
                    System.out.println(demtimes.get(k).attr("datetime"));

                /* VIRKER -ISH
                Elements cookdude1 = documents.get(i).select("span.prepTime__item--time");
                Elements cookdude2 = documents.get(i).select(".prepTime__item time");

                for (int j=0; j < cookdude2.size(); j++)
                    System.out.println(cookdude1.get(j).ownText() + cookdude2.get(j).ownText());
*/

                caloriesInRecipe.add(documents.get(i).select(".calorie-count").text());
                System.out.println(caloriesInRecipe);


                TimeUnit.SECONDS.sleep(2);
            }

      } catch(IOException | InterruptedException e) {
            e.printStackTrace();
      }
        System.out.println("done");
    }

  }
