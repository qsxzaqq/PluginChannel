package cc.i9mc.pluginchannel.message;

import cc.i9mc.pluginchannel.util.ByteUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ReadResult {

    private List<MessageBuilder> currentMessages;
    private boolean full;

    public ReadResult(List<MessageBuilder> currentMessages, boolean full) {
        this.currentMessages = currentMessages;
        this.full = full;
    }

    public String build() {
        currentMessages.sort(Comparator.comparingInt(MessageBuilder::getProgressCurrent));
        return ByteUtils.deSerialize(currentMessages.stream().map(MessageBuilder::getData).collect(Collectors.joining()));
    }

    // *********************************
    //
    //        Getter and Setter
    //
    // *********************************

    public boolean isFull() {
        return full;
    }

    public List<MessageBuilder> getCurrentMessages() {
        return currentMessages;
    }
}
