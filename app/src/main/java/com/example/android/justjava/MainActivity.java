package com.example.android.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app send email by available sellers and
 * displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private boolean addWhippedCream  = false;
    private boolean addChocolate  = false;
    private int quantity = 0;

    /**
     * Returns written name.
     */
    private String getName(){
        EditText editText = (EditText) findViewById(R.id.edit_name);
        String name = editText.getText().toString();
        return name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(int quantity) {
        int price;
        if(addWhippedCream && addChocolate) price = quantity * 8;
        else if(addWhippedCream) price = quantity * 6;
        else if(addChocolate) price = quantity * 7;
        else price = quantity * 5;
        return price;
    }

    /**
     * This method is called in submitOrder method to fill the Order
     * desired ingredients.
     */
    @SuppressLint("StringFormatInvalid")
    private  String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        return (getString(R.string.order_summary_name, name) +
                "\n" + getString(R.string.add_wipped_cream, addWhippedCream)  +
                "\n" + getString(R.string.add_chocolate, addChocolate) +
                "\n" + getString(R.string.total_quantity, String.valueOf(quantity)) +
                "\n" + getString(R.string.total, String.valueOf(price)) +
                "\n" + getString(R.string.thank_you));
    }

    /**
     * This method is called when the order button is clicked to send email
     * with the Order text to necessary address by available seller.
     */
    public void submitOrder(View view) {
        int price = calculatePrice(quantity);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT,
                createOrderSummary(getName(), price, addWhippedCream, addChocolate));

        startActivity(Intent.createChooser(intent, "Send Email"));
        Log.v("MainActivity", "Email is sent");
        if (intent.resolveActivity(getPackageManager()) !=null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the +1 button is clicked.
     */
    public void increment(View view) {
        if(quantity > 100) {
            displayQuantity(quantity = 100);
            bigQuantity();
        }
        else displayQuantity(quantity++);
    }

    /**
     * This method is called when the -1 button is clicked.
     */
    public void decrement(View view) {
        if(quantity <= 1) {
            displayQuantity(quantity = 1);
            negQuantity();
        }
        else displayQuantity(--quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the checkedWippedCream button is clicked.
     */
    public boolean checkedWippedCream(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.wipped_cream);
        if(checkBox.isChecked()) {
            checkBox.setChecked(true);
            addWhippedCream  = true;
            Log.v("MainActivity", "checkBox status is " + addWhippedCream);
             return addWhippedCream;
        }
        addWhippedCream  = false;
        return addWhippedCream;
    }

    /**
     * This method is called when the checkedChocolate button is clicked.
     */
    public boolean checkedChocolate(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate);
        if(checkBox.isChecked()) {
            checkBox.setChecked(true);
            addChocolate  = true;
            Log.v("MainActivity", "checkBox status is " + addChocolate);
            return addChocolate;
        }
        addChocolate  = false;
        return addChocolate;
    }

    /**
     * This method displays "You cannot have less then 1 coffee".
     */
    private void negQuantity() {
        Toast toastNegQu = Toast.makeText(getApplicationContext(),
                "You cannot have less then 1 coffee", Toast.LENGTH_SHORT);
        toastNegQu.show();
    }

    /**
     * This method displays "You cannot have more then 100 coffee".
     */
    private void bigQuantity() {
        Toast toastBigQu = Toast.makeText(getApplicationContext(),
                "You cannot have more then 100 coffee", Toast.LENGTH_SHORT);
        toastBigQu.show();
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}