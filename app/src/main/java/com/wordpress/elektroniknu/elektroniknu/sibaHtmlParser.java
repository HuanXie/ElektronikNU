package com.wordpress.elektroniknu.elektroniknu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class sibaHtmlParser implements  htmlParser{

    private List<Product> products;

    public sibaHtmlParser() {
    }

    public void startFetch(){
        fetchProducts();
    }

    public List<Product> getProducts(){
        return products;
    }

    private void fetchProducts(){
        Document Doc = null;
        try{
            Doc = Jsoup.connect("http://www.siba.se/aktuella-kampanjer/veckans-erbjudande").get(); // HTML file from their website
        }catch(IOException ex){ // Catch exception
            Logger.getLogger(sibaHtmlParser.class.getName()).log(Level.SEVERE, null, ex); // Print out at log
        }
        if(Doc != null) {

            org.jsoup.select.Elements links = Doc.select("div.info h2 a");

            Product[] Products = new Product[links.size()];
            for(int i = 0; i < links.size(); i++){
                Products[i] = new Product("Siba");
            }

            int i = 0;
            for(Element e: links){
                Products[i].setUrl(e.attr("abs:href"));
                Products[i].setProductName(e.ownText());
                i++;
            }

            links = Doc.select("div.product-box-price");
            i = 0;
            for(Element e: links){
                Products[i].setProductPrice(e.ownText().replace(" ", ""));
                i++;
            }

            links = Doc.select("img.js-responsive-image");
            i = 0;
            for(Element e: links){
                Products[i].setProductImageUrl(e.attr("data-src").replace("&amp", "&") + "&width=1000");
                i++;
            }

            links = Doc.select("div.description li");
            i = 0;
            int number = 0;
            String[] description = new String[3];
            for(Element e: links){
                description[number] = e.text();
                if(number == 2){
                    number = 0;
                    Products[i].setProductDescription(description);
                    i++;
                    description = new String[3];
                }else {
                    number++;
                }
            }
            long startTime = System.currentTimeMillis();
            setSibaProductsCategoryName.setProductsCategoryName(Products);
            long endTime = System.currentTimeMillis();
            System.out.println("Product name " + (endTime - startTime) + " milliseconds");
            products = Arrays.asList(Products);
        }
    }
}
