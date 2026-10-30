
package com.royalbluetycoon.game;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    int money = 100;
    int level = 1;
    int sales = 0;
    int upgrade = 1;

    boolean pizzaUnlocked = false;
    boolean burgerUnlocked = false;
    boolean iceCreamUnlocked = false;

    TextView moneyText, levelText, salesText, messageText;
    LinearLayout screen;

    final int blue = Color.rgb(5, 19, 48);
    final int lightBlue = Color.rgb(16, 62, 119);
    final int gold = Color.rgb(255, 207, 65);
    final int white = Color.WHITE;

    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("RoyalBlueSave", MODE_PRIVATE);
        loadGame();
        showGame();
    }

    void loadGame() {
        money = prefs.getInt("money", 100);
        level = prefs.getInt("level", 1);
        sales = prefs.getInt("sales", 0);
        upgrade = prefs.getInt("upgrade", 1);
        pizzaUnlocked = prefs.getBoolean("pizza", false);
        burgerUnlocked = prefs.getBoolean("burger", false);
        iceCreamUnlocked = prefs.getBoolean("icecream", false);
    }

    void saveGame() {
        prefs.edit()
            .putInt("money", money)
            .putInt("level", level)
            .putInt("sales", sales)
            .putInt("upgrade", upgrade)
            .putBoolean("pizza", pizzaUnlocked)
            .putBoolean("burger", burgerUnlocked)
            .putBoolean("icecream", iceCreamUnlocked)
            .apply();
    }

    void showGame() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(blue);

        screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setPadding(16, 24, 16, 24);
        scroll.addView(screen);

        setContentView(scroll);

        TextView crown = new TextView(this);
        crown.setText("👑");
        crown.setTextSize(48);
        crown.setGravity(Gravity.CENTER);
        screen.addView(crown);

        TextView title = new TextView(this);
        title.setText("ROYAL BLUE TYCOON");
        title.setTextColor(gold);
        title.setTextSize(28);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        screen.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("BUILD YOUR BUSINESS EMPIRE");
        subtitle.setTextColor(white);
        subtitle.setTextSize(13);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(5, 8, 5, 22);
        screen.addView(subtitle);

        moneyText = addStat("💰 MONEY: $" + money);
        levelText = addStat("⭐ LEVEL: " + level);
        salesText = addStat("👥 CUSTOMERS: " + sales);

        addHeading("YOUR BUSINESS");

        addShop("🥤 ROYAL DRINKS",
                "Refreshing drinks", 5);

        addShop("🍬 CANDY CENTRAL",
                "Candy and sweet treats", 4);

        addShop("🍿 SNACK STATION",
                "Delicious snacks", 6);

        addHeading("BUSINESS UPGRADES");

        addButton("🏆 UPGRADE BUSINESS - $" + (75 * upgrade), () -> {
            int cost = 75 * upgrade;

            if (money >= cost) {
                money -= cost;
                upgrade++;
                messageText.setText("Business upgraded!");
                refresh();
            } else {
                messageText.setText("You need more money!");
            }
        });

        addHeading("UNLOCK NEW SHOPS");

        addUnlockableShop("🍕 PIZZA PALACE",
                "pizza", 250, 15);

        addUnlockableShop("🍔 BURGER FACTORY",
                "burger", 500, 25);

        addUnlockableShop("🍦 ICE CREAM WORLD",
                "icecream", 1000, 40);

        addHeading("BUSINESS REPORT");

        messageText = new TextView(this);
        messageText.setText("Welcome to your business empire!");
        messageText.setTextColor(gold);
        messageText.setTextSize(16);
        messageText.setGravity(Gravity.CENTER);
        messageText.setPadding(10, 20, 10, 20);
        screen.addView(messageText);

        addButton("💾 SAVE GAME", () -> {
            saveGame();
            messageText.setText("Game saved!");
        });

        addButton("🔄 RESET GAME", () -> {
            money = 100;
            level = 1;
            sales = 0;
            upgrade = 1;
            pizzaUnlocked = false;
            burgerUnlocked = false;
            iceCreamUnlocked = false;
            saveGame();
            showGame();
        });

        refresh();
    }

    TextView addStat(String text) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextColor(gold);
        view.setTextSize(19);
        view.setTypeface(null, Typeface.BOLD);
        view.setGravity(Gravity.CENTER);
        view.setBackgroundColor(lightBlue);
        view.setPadding(12, 15, 12, 15);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 5, 0, 5);
        screen.addView(view, params);
        return view;
    }

    void addHeading(String text) {
        TextView heading = new TextView(this);
        heading.setText("🏆 " + text);
        heading.setTextColor(gold);
        heading.setTextSize(21);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(0, 25, 0, 12);
        screen.addView(heading);
    }

    void addShop(String name, String description, int price) {
        LinearLayout card = makeCard();

        TextView title = new TextView(this);
        title.setText(name);
        title.setTextColor(gold);
        title.setTextSize(21);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        card.addView(title);

        TextView info = new TextView(this);
        info.setText(description + "\nEarn $" + (price * upgrade) + " per sale");
        info.setTextColor(white);
        info.setTextSize(15);
        info.setGravity(Gravity.CENTER);
        info.setPadding(5, 10, 5, 10);
        card.addView(info);

        addButtonTo(card, "SELL NOW", () -> sell(price));
    }

    void addUnlockableShop(String name, String type,
                           int cost, int price) {
        LinearLayout card = makeCard();

        TextView title = new TextView(this);
        title.setText(name);
        title.setTextColor(gold);
        title.setTextSize(21);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        card.addView(title);

        TextView info = new TextView(this);
        info.setText("Unlock cost: $" + cost +
                     "\nEarn $" + (price * upgrade) + " per sale");
        info.setTextColor(white);
        info.setGravity(Gravity.CENTER);
        info.setPadding(5, 10, 5, 10);
        card.addView(info);

        boolean unlocked = isUnlocked(type);

        if (unlocked) {
            addButtonTo(card, "SELL NOW", () -> sell(price));
        } else {
            addButtonTo(card, "UNLOCK SHOP - $" + cost, () -> {
                if (money >= cost) {
                    money -= cost;
                    setUnlocked(type);
                    messageText.setText(name + " is now open!");
                    saveGame();
                    showGame();
                } else {
                    messageText.setText("You need more money!");
                }
            });
        }
    }

    LinearLayout makeCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setPadding(15, 18, 15, 18);
        card.setBackgroundColor(lightBlue);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(-1, -2);

        params.setMargins(0, 7, 0, 10);
        screen.addView(card, params);
        return card;
    }

    boolean isUnlocked(String type) {
        if (type.equals("pizza")) return pizzaUnlocked;
        if (type.equals("burger")) return burgerUnlocked;
        if (type.equals("icecream")) return iceCreamUnlocked;
        return false;
    }

    void setUnlocked(String type) {
        if (type.equals("pizza")) pizzaUnlocked = true;
        if (type.equals("burger")) burgerUnlocked = true;
        if (type.equals("icecream")) iceCreamUnlocked = true;
    }

    void sell(int price) {
        int earnings = price * upgrade;
        money += earnings;
        sales++;
        level = sales / 10 + 1;

        messageText.setText("Sale complete! You earned $" + earnings);
        saveGame();
        refresh();
    }

    void addButton(String text, Runnable action) {
        Button button = makeButton(text);
        button.setOnClickListener(v -> action.run());
        screen.addView(button);
    }

    void addButtonTo(LinearLayout parent,
                     String text, Runnable action) {
        Button button = makeButton(text);
        button.setOnClickListener(v -> action.run());
        parent.addView(button);
    }

    Button makeButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(blue);
        button.setTextSize(15);
        button.setTypeface(null, Typeface.BOLD);
        button.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(gold));
        return button;
    }

    void refresh() {
        moneyText.setText("💰 MONEY: $" + money);
        levelText.setText("⭐ LEVEL: " + level);
        salesText.setText("👥 CUSTOMERS: " + sales);
        saveGame();
    }
}
