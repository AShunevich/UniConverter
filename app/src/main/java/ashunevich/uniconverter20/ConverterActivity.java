package ashunevich.uniconverter20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ConverterActivity  extends AppCompatActivity {

    @BindView(R.id.spinner_value)
    Spinner spinnerValue;
    @BindView(R.id.spinner_result)
    Spinner spinnerResult;
    @BindView(R.id.valueEdit)
    TextView valueEdit;
    @BindView(R.id.resultView)
    TextView resultView;
    @BindView(R.id.valueName)
    TextView valueName;


    protected double getEnteredValue;
    protected String getValueSpinnerFrom, getValueSpinnerTo;
    protected ArrayAdapter<String> tempAdapter, areaAdapter, lengthAdapter, speedAdapter, timeAdapter,
            volumeAdapter, currencyAdapter, weightAdapter, forceAdapter;
    protected HashMap<String, String> hm;
    private String SAVED_INTENT_NAME = "intentName";
    private final String SAVED_VALUE = "savedValue";
    private final String SAVED_RESULT = "saveResult";

    @Override
    protected void onSaveInstanceState (Bundle savedInstanceState){
        savedInstanceState.putString(SAVED_INTENT_NAME,valueName.getText().toString());
        savedInstanceState.putString(SAVED_VALUE,valueEdit.getText().toString());
        savedInstanceState.putString(SAVED_RESULT,resultView.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String GET_NAME = intent.getStringExtra("getName");
        hm = new HashMap<String, String>();
        valueName.setText(GET_NAME);

        tempAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.temperature_array));
        areaAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.area));
        lengthAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.length));
        speedAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.speed));
        timeAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.time_array));
        volumeAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.volume));
        weightAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.weight));
        currencyAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.currency));
        forceAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.force));
        setSpinnersAdapters(spinnerValue, spinnerResult);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        valueName.setText(savedInstanceState.getString(SAVED_INTENT_NAME));
        valueEdit.setText(savedInstanceState.getString(SAVED_VALUE));
        resultView.setText(savedInstanceState.getString(SAVED_RESULT));
        setSpinnersAdapters(spinnerValue,spinnerResult);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @OnClick({R.id.button_decimal, R.id.but_one, R.id.but_two, R.id.but_three,
            R.id.but_four, R.id.but_five, R.id.but_six, R.id.but_seven,
            R.id.button_eight, R.id.button_zero,R.id.but_nine, R.id.button_dzero, R.id.clearButton,
            R.id.getResult})
    public void setViewOnClickEvent(View view) {
        switch (view.getId()) {
            case R.id.but_one:
                readAndSetText("1");break;
            case R.id.but_two:
                readAndSetText("2");break;
            case R.id.but_three:
                readAndSetText("3");break;
            case R.id.but_four:
                readAndSetText("4");break;
            case R.id.but_five:
                readAndSetText("5");break;
            case R.id.but_six:
                readAndSetText("6");break;
            case R.id.but_seven:
                readAndSetText("7");break;
            case R.id.button_eight:
                readAndSetText("8");break;
            case R.id.but_nine:
                readAndSetText("9");break;
            case R.id.button_zero:
                readAndSetText("0");break;
            case R.id.button_dzero:
                readAndSetText("00");break;
            case R.id.button_decimal:
                readAndSetText(".");break;
            case R.id.clearButton:
                resultView.setText("");valueEdit.setText("");break;
            case R.id.getResult:
                getValueSpinnerFrom = spinnerValue.getSelectedItem().toString();
                getValueSpinnerTo = spinnerResult.getSelectedItem().toString();
                getEnteredValue = Double.parseDouble(valueEdit.getText().toString());

                if (valueName.getText().toString().equals(getResources().getString(R.string.currency_button))){
                    convertCurrecyData(getValueSpinnerFrom,getValueSpinnerTo,getEnteredValue,resultView);
            }
            else {
                    ConverterAdapter.ConvertValues(getValueSpinnerFrom, getValueSpinnerTo, getEnteredValue, resultView);
                }break;
        }

    }

    private void setSpinnersAdapters(Spinner spinnerValue, Spinner spinnerResult) {
        String activeValue = valueName.getText().toString();

        switch (activeValue) {
            case "Weight":
                spinnerValue.setAdapter(weightAdapter);
                spinnerResult.setAdapter(weightAdapter);
                break;
            case "Area":
                spinnerValue.setAdapter(areaAdapter);
                spinnerResult.setAdapter(areaAdapter);
                break;
            case "Time":
                spinnerValue.setAdapter(timeAdapter);
                spinnerResult.setAdapter(timeAdapter);
                break;
            case "Length":
                spinnerValue.setAdapter(lengthAdapter);
                spinnerResult.setAdapter(lengthAdapter);
                break;
            case "Volume":
                spinnerValue.setAdapter(volumeAdapter);
                spinnerResult.setAdapter(volumeAdapter);
                break;
            case "Temperature":
                spinnerValue.setAdapter(tempAdapter);
                spinnerResult.setAdapter(tempAdapter);
                break;
            case "Force":
                spinnerValue.setAdapter(forceAdapter);
                spinnerResult.setAdapter(forceAdapter);
                break;
            case "Currency":
                getJsonOnlineData();
                spinnerValue.setAdapter(currencyAdapter);
                spinnerResult.setAdapter(currencyAdapter);
                break;
            case "Speed":
                spinnerValue.setAdapter(speedAdapter);
                spinnerResult.setAdapter(speedAdapter);
                break;
        }

    }

    private void readAndSetText(String toSet) {
        String readText = valueEdit.getText().toString();
        String stringToSet = readText + toSet;
        valueEdit.setText(stringToSet);
    }


    private void getJsonOnlineData() {
        String url = "https://api.exchangeratesapi.io/latest";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Tag", response.toString());
                //USD,GBP,IDR,PLN,NZD,RUB

                try {
                    JSONObject phone = response.getJSONObject("rates");
                    String USD = phone.getString("USD");
                    String GBP = phone.getString("GBP");
                    String IDR = phone.getString("IDR");
                    String PLN = phone.getString("PLN");
                    String NZD = phone.getString("NZD");
                    String RUB = phone.getString("RUB");

                    hm.put("United States Dollar/USD", USD );
                    hm.put("Great Britain Pound/GBP",GBP);
                    hm.put("Indonesian rupiah/IPR", IDR );
                    hm.put("Polish złoty/PLN",PLN);
                    hm.put("New Zealand dollar/NZD", NZD );
                    hm.put("Russian Ruble/RUB",RUB);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }

        });

        RequestQueue queue = Volley.newRequestQueue(this);
        // Adding request to request queue
        queue.add(jsonObjReq);
    }

    private void convertCurrecyData(String txtFromSpinner1, String txtFromSpinner2 , Double enteredValue, TextView resultView){
            txtFromSpinner1 = spinnerValue.getSelectedItem().toString();
            txtFromSpinner2 = spinnerResult.getSelectedItem().toString();

            try{
                double initRate = Double.valueOf(hm.get(txtFromSpinner1));
                double targetRate = Double.valueOf(hm.get(txtFromSpinner2));
                String resultFinal = String.valueOf(String.format("%.2f", ((targetRate * enteredValue) / initRate)));
                resultView.setText(resultFinal);
            }
            catch (Exception e){
                Log.d(" Exception","exeption catched") ;
            }
    }
}
