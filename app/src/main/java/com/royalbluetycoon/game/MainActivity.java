
package com.royalbluetycoon.game;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    int money = 100;
    int level = 1;
    int sales = 0;
    int upgrade = 1;

    TextView moneyText;
    TextView levelText;
    TextView salesText;
    TextView messageText;

    LinearLayout screen;

    int blue = Color.rgb(7, 25, 58);
    int lightBlue = Color.rgb(20, 76, 145);
    int gold = Color.rgb(255, 205, 65);
    int white = Color.WHITE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showGame();
    }

    void showGame() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(blue);

        screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setPadding(18, 25, 18, 25);

        scroll.addView(screen);
        setContentView(scroll);

        TextView title = new TextView(this);
        title.setText("👑 ROYAL BLUE TYCOON");
        title.setTextColor(gold);
        title.setTextSize(27);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(5, 15, 5, 15);
        screen.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("BUILD YOUR BUSINESS EMPIRE");
        subtitle.setTextColor(white);
        subtitle.setTextSize(14);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(5, 5, 5, 20);
        screen.addView(subtitle);

        moneyText = addStat("💰 MONEY: $" + money);
        levelText = addStat("⭐ LEVEL: " + level);
        salesText = addStat("👥 CUSTOMERS: " + sales);

        addHeading("🏬 YOUR SHOPS");

        addShop("🥤 ROYAL DRINKS",
                "Sell refreshing drinks",
                5, "SELL DRINK");

        addShop("🍬 CANDY CENTRAL",
                "Sell sweet candy",
                4, "SELL CANDY");

        addShop("🍿 SNACK STATION",
                "Sell delicious snacks",
                6, "SELL SNACK");

        addHeading("🏆 BUSINESS UPGRADES");

        Button upgradeButton = makeButton(
                "UPGRADE BUSINESS - $" + (75 * upgrade));

        upgradeButton.setOnClickListener(v -> {
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

        screen.addView(upgradeButton);

        addHeading("📊 BUSINESS REPORT");

        messageText = new TextView(this);
        messageText.setText("Welcome to your business empire!");
        messageText.setTextColor(gold);
        messageText.setTextSize(16);
        messageText.setGravity(Gravity.CENTER);
        messageText.setPadding(10, 20, 10, 20);
        screen.addView(messageText);

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
                new LinearLayout.LayoutParams(
                        -1, -2);

        params.setMargins(0, 5, 0, 5);
        screen.addView(view, params);

        return view;
    }

    void addHeading(String text) {
        TextView heading = new TextView(this);
        heading.setText(text);
        heading.setTextColor(gold);
        heading.setTextSize(22);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(0, 25, 0, 12);
        screen.addView(heading);
    }

    void addShop(String name, String description,
                 int price, String buttonText) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER);
        card.setPadding(15, 18, 15, 18);
        card.setBackgroundColor(lightBlue);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(-1, -2);

        cardParams.setMargins(0, 7, 0, 10);
        screen.addView(card, cardParams);

        TextView shopName = new TextView(this);
        shopName.setText(name);
        shopName.setTextColor(gold);
        shopName.setTextSize(21);
        shopName.setTypeface(null, Typeface.BOLD);
        shopName.setGravity(Gravity.CENTER);
        card.addView(shopName);

        TextView info = new TextView(this);
        info.setText(description + "\nEarn $" +
                (price * upgrade) + " per sale");
        info.setTextColor(white);
        info.setTextSize(15);
        info.setGravity(Gravity.CENTER);
        info.setPadding(5, 10, 5, 10);
        card.addView(info);

        Button sellButton = makeButton(buttonText);

        sellButton.setOnClickListener(v -> {
            int earnings = price * upgrade;

            money += earnings;
            sales++;
            level = (sales / 10) + 1;

            messageText.setText(
                    "Sale complete! You earned $" + earnings);

            refresh();
        });

        card.addView(sellButton);
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
    }
}
