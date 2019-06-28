package ashunevich.uniconverter20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SphereActivity extends AppCompatActivity {

    @BindView(R.id.circleAreaValue)  TextView circleArea;
    @BindView(R.id.diamValue)  TextView circleDiameter;
    @BindView(R.id.circValue)  TextView circleCirc;
    @BindView(R.id.radValue) TextView circleRad;
    @BindView(R.id.sphereAreaValue) TextView sphereArea;
    @BindView(R.id.sphereVolumeValue) TextView sphereVolume;
    @BindView(R.id.hemiAreaValue) TextView hemiArea;
    @BindView(R.id.hemiVolumeValue) TextView hemiVolume;
    @BindView(R.id.value_spinner) Spinner sphereSpinner;
    @BindView(R.id.valSpinner) Spinner valSpinner;
    @BindView(R.id.valueEdit) EditText value;
    @BindView(R.id.valueName) TextView valueName;
    @BindView(R.id.clearButton) Button clear;
    @BindView(R.id.areaID) TextView areaID;
    @BindView(R.id.diamID) TextView diamID;
    @BindView(R.id.circID) TextView circID;
    @BindView(R.id.radiusID) TextView radiusID;
    @BindView(R.id.sphAreaID) TextView sphAreaID;
    @BindView(R.id.hmareaID) TextView hmareaID;
    @BindView(R.id.volumeSID) TextView volumeSID;
    @BindView(R.id.volumehsID) TextView volumehsID;

    //TODO (2)добавить единицы измерения
    //TODO (3)переделать layout


    protected ArrayAdapter<String> dataParam, valParam;
    protected String SPINNER_STRING,VAL_SPINNER_STRING;
    protected double getEnteredValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.angle_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String GET_NAME = intent.getStringExtra("getName");
        valueName.setText(GET_NAME);
        dataParam = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.circlesAndSpheres));
        sphereSpinner.setAdapter(dataParam);
        valParam= new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.valuess));
        valSpinner.setAdapter(valParam);
        spinnerListener();
        valSpinnerListener();
        addTextWatcher();


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    private void valSpinnerListener(){
        valSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                VAL_SPINNER_STRING = valSpinner.getSelectedItem().toString();
                if(VAL_SPINNER_STRING.equals(areaID.getText().toString())){
                    setValuesAccViews(VAL_SPINNER_STRING);
                }
                else{
                    setValuesAccViews(VAL_SPINNER_STRING);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }



    private void spinnerListener(){
        sphereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (TextUtils.isEmpty(value.getText().toString())){
                    clear();
                }
                else {
                    getEnteredValue = Double.parseDouble(value.getText().toString());
                    SPINNER_STRING = sphereSpinner.getSelectedItem().toString();
                    SphereAdapter.findParam(getEnteredValue,SPINNER_STRING, circleArea, circleDiameter,  circleCirc,  circleRad, sphereArea, sphereVolume,
                            hemiArea,  hemiVolume);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

    }

    private void addTextWatcher() {

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(value.getText().toString())){
                    clear();
                }
                else {
                    getEnteredValue = Double.parseDouble(value.getText().toString());
                    SPINNER_STRING = sphereSpinner.getSelectedItem().toString();
                    SphereAdapter.findParam(getEnteredValue,SPINNER_STRING, circleArea, circleDiameter,  circleCirc,  circleRad, sphereArea, sphereVolume,
                            hemiArea,  hemiVolume);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });
    }

    public void auxSpinnerWatcher(){

    }

    private void clear (){
        circleRad.setText("");
        circleDiameter.setText("");
        circleArea.setText("");
        circleCirc.setText("");
        sphereArea.setText("");
        sphereVolume.setText("");
        hemiArea.setText("");
        hemiVolume.setText("");
    }

    private void setValuesAccViews(String string){

        String two = getResources().getString(R.string.top2);
        String three = getResources().getString(R.string.top3);

            areaID.setText(String.valueOf(string+two));
            diamID.setText(string);
            circID.setText(string);
            radiusID.setText(string);
            sphAreaID.setText(String.valueOf(string+two));
            hmareaID.setText(String.valueOf(string+two));
            volumeSID.setText(String.valueOf(string+three));
            volumehsID.setText(String.valueOf(string+three));
    }



}