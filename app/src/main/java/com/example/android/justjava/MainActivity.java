package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bb = (Button)findViewById(R.id.share);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullUrl = "https://www.facebook.com/sharer/sharer.php?u=example.org";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(fullUrl));
                    i.putExtra(Intent.EXTRA_TEXT,"go fuckin go");
                    startActivity(i);

            }
        });
    }


    /**
     * this method is called when the + button is clicked.
     */

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(MainActivity.this, "Can not be more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * this method is called when the - button is clicked
     **/

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(MainActivity.this, "Can not be less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamChekBox = (CheckBox) findViewById(R.id.checkbox_cream);
        boolean hasWhippedCream = whippedCreamChekBox.isChecked();

        CheckBox whippedChocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean hasWhippedChocolate = whippedChocolateCheckBox.isChecked();

        EditText nametxt = (EditText) findViewById(R.id.name_edit_text);
        String coName = nametxt.getText().toString();

        int price = calculatePrice(hasWhippedChocolate, hasWhippedCream);
        String message = createOrderSummary(price, hasWhippedCream, hasWhippedChocolate, coName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary for master : " + coName);
        intent.putExtra(intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * Calculates the price of the order.
     *
     * @param addChocolate is Whether or not the User wants to add Chocolate
     * @param addCream     is whether or not the User wants to add Cream
     * @return total price
     */
    private int calculatePrice(boolean addCream, boolean addChocolate) {
        int basePrice = 5;

        if (addChocolate) {
            basePrice = basePrice + 1;
        }
        if (addCream) {
            basePrice = basePrice + 2;
        }

        int price = quantity * basePrice;
        return price;
    }

    ///////////////////////////

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param coName          is customer name
     * @return text summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String coName) {
        String message = "Name : " + coName;
        message += "\nhas Cream ? " + addWhippedCream;
        message += "\nhas Chocolate ? " + addChocolate;
        message += "\nQuantity: " + quantity;
        message = message + "\nTotal " + price + "$";
        message = message + "\nThank You!";
        return message;

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}