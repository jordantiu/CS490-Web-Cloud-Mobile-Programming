package com.example.karthik.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    final double TOPPING_PRICE = .50;
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String boolToString(boolean bool){
        return bool?(getString(R.string.yes)):(getString(R.string.no));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }


    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {

            Log.i("MainActivity", "Please select less than one hundred pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {

            Log.i("MainActivity", "Please select at least one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;

        }
    }

    public void order(View view) {
        EditText orderName = (EditText) findViewById(R.id.order_name);
        CheckBox cheese = (CheckBox) findViewById(R.id.cheese);
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni);
        CheckBox sausage = (CheckBox) findViewById(R.id.sausage);
        CheckBox bacon = (CheckBox) findViewById(R.id.bacon);
        CheckBox peppers = (CheckBox) findViewById(R.id.peppers);
        CheckBox pineapple = (CheckBox) findViewById(R.id.pineapple);
        CheckBox mushrooms = (CheckBox) findViewById(R.id.mushrooms);

        RadioGroup sauceGroup = (RadioGroup) findViewById(R.id.sauce);
        int sauceId = sauceGroup.getCheckedRadioButtonId();

        String sauce = "No Sause";
        if (sauceId == R.id.red) {
            sauce = "Red";
        } else if (sauceId == R.id.white) {
            sauce = "White";
        }

        String details = "Order name: " + orderName.getText().toString();
        details += "\n\n Quantity: " + quantity;
        details += "\n\n Sauce: " + sauce;
        details += "\n Add Cheese: " + boolToString(cheese.isChecked());
        details += "\n Add Pepperoni: " + boolToString(pepperoni.isChecked());
        details += "\n Add Sausage: " + boolToString(sausage.isChecked());
        details += "\n Add Bacon: " + boolToString(bacon.isChecked());
        details += "\n Add Peppers: " + boolToString(peppers.isChecked());
        details += "\n Add Pineapple: " + boolToString(pineapple.isChecked());
        details += "\n Add Mushrooms: " + boolToString(mushrooms.isChecked());

        int numToppings = 0;
        if (cheese.isChecked()) numToppings++;
        if (pepperoni.isChecked()) numToppings++;
        if (sausage.isChecked()) numToppings++;
        if (bacon.isChecked()) numToppings++;
        if (peppers.isChecked()) numToppings++;
        if (pineapple.isChecked()) numToppings++;
        if (mushrooms.isChecked()) numToppings++;

        Spinner sizeSpinner = findViewById(R.id.size);
        int size = sizeSpinner.getSelectedItemPosition();

        int pizzaPrice = 7;
        if (size == 0) {
            pizzaPrice = 10;
        } else if (size == 1) {
            pizzaPrice = 8;
        }

        double price = quantity * (pizzaPrice + (TOPPING_PRICE * numToppings));

        details += "\n\n Size: " + sizeSpinner.getSelectedItem().toString();
        details += "\n\n Total: $" + String.format("%.2f", price);;
        details += "\n\n Thank you for placing your order with the MyOder Application!" ;

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        emailIntent.putExtra(Intent.EXTRA_CC, "");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Confirmation");
        emailIntent.putExtra(Intent.EXTRA_TEXT, details);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i(TAG, "Email sent!");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
