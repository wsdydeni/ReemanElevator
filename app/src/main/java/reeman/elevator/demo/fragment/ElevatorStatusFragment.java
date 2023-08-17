package reeman.elevator.demo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import reeman.elevator.demo.MainActivity;
import reeman.elevator.demo.R;
import reeman.elevator.demo.model.config.Lifts;
import reeman.elevator.demo.utils.LogUtil;


public class ElevatorStatusFragment extends Fragment {

    public ElevatorStatusFragment() {
        super(R.layout.fragment_elevator_status);
    }

    private TextView curFloorText;
    private TextView doorStateText;

    private volatile int doorStateFloor = -1;
    private volatile String doorState = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.isWaitMonitor = false;
                Navigation.findNavController(view).navigate(R.id.action_elevatorStatusFragment_to_buildingFragment);
            }
        });
        curFloorText = view.findViewById(R.id.text_cur_floor);
        doorStateText = view.findViewById(R.id.text_door_state);
        LogUtil.print(Log.INFO, "test", String.valueOf(getArguments()));
        MainActivity mainActivity = (MainActivity) requireActivity();
        if (getArguments() != null) {
            String startFloor = getArguments().getString("startFloor", "");
            String endFloor = getArguments().getString("endFloor", "");
            Lifts lifts = (Lifts) getArguments().getSerializable("lift");
            mainActivity.setOnLiftFloorChangeListener(new MainActivity.OnLiftFloorChangeListener() {
                @Override
                public void onCurFloorChange(int curFloor) {
                    if(!doorState.equals("OPENING")) {
                        curFloorText.setText("CurrentFloor: " + (curFloor));
                    }
                    if((curFloor) != doorStateFloor && doorState.equals("CLOSED")) {
                        doorStateText.setText("");
                    }
                }
            });
            mainActivity.setOnLiftDoorStateChangeListener(new MainActivity.OnLiftDoorStateChangeListener() {
                @Override
                public void onDoorStateChange(String floor, String state) {
                    doorStateFloor = Integer.parseInt(floor);
                    doorState = state;
                    curFloorText.setText("CurrentFloor: " + doorStateFloor);
                    doorStateText.setText("Door State: " + state);
                    if(state.equals("CLOSED") && String.valueOf(doorStateFloor).equals(endFloor)) {
                        // 提示乘梯完成
                        new AlertDialog.Builder(requireContext())
                                .setCancelable(false)
                                .setTitle("Hint")
                                .setMessage("Arrived the target floor")
                                .setNegativeButton("ok, return select buildings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mainActivity.handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(!mainActivity.isWaitMonitor) return;
                                                dialog.dismiss();
                                                mainActivity.isWaitMonitor = false;
                                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                                                        .navigate(R.id.action_elevatorStatusFragment_to_buildingFragment);
                                            }
                                        });
                                    }
                                })
                                .show();
                    }
                }
            });
            mainActivity.destinationCall(startFloor, endFloor, lifts.getLift_id());
        }
    }

}
