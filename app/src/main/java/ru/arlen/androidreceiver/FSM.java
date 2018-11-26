package ru.arlen.androidreceiver;

enum State {
    A, B, C, D, E
}

public class FSM {
    public static final FSM INSTANCE = new FSM();
    private State mState;

    private FSM() {mState = State.A;}

    public State getCurrentState(){
        return mState;
    }
    public void changeState(int stateId){
        mState = State.values()[stateId];
    }
}
