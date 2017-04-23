package com.delta.swiftq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;
import java.util.Map;

public class CustomAdapter extends ArrayAdapter<Order> {
    private final MainActivity activity;

    public CustomAdapter(MainActivity activity, List<Order> orders) {
        super(activity, R.layout.item_transaction, orders);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get order
        Order order = getItem(position);

        // Check if there's view we can reuse, if not create
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);
        }

        // Get handle to item's layout
        View view = convertView.findViewById(R.id.topLayout);
        // Set its tag to position on ListView
        view.setTag(position);
        // Set on click listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get position of item clicked from tag set a few lines above
                int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
                showOrder(getItem(position));
            }
        });

        ToggleButton toggleButton = (ToggleButton) convertView.findViewById(R.id.toggleButton4);
        toggleButton.setTag(position);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String transactionID = getItem((Integer) buttonView.getTag()).orderId;
                if (isChecked) {
                    activity.changeStatus(transactionID, "1");
                } else {
                    activity.changeStatus(transactionID, "0");
                }
            }
        });

        //Populate fields
        ((TextView) convertView.findViewById(R.id.fullname)).setText(order.fullname);
        ((TextView) convertView.findViewById(R.id.magicWords)).setText(order.keywords);
        ((TextView) convertView.findViewById(R.id.hour)).setText(order.collectionTime);
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<Item, Integer> entry : order.items.entrySet()) {
            sb.append(String.format("%3d - %s\n", entry.getValue(), entry.getKey().getName()));
            entry.getValue();
        }
        ((TextView) convertView.findViewById(R.id.items)).setText(sb.toString());

        return convertView;
    }

    private void showOrder(Order order) {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Details")
                .setMessage(order.toStringDetailed())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIconAttribute(android.R.attr.dialogIcon)
                .show();
    }
}

