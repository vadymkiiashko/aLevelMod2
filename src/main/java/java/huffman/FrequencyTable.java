package huffman;


import files.SymbolType;
import utilities.Constants;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FrequencyTable {
    private List<SymbolFrequency> frequencies;

    public FrequencyTable() {
        frequencies = new ArrayList<>();
    }

    public int size() {
        return frequencies.size();
    }

    public boolean containsSymbol(int symbol) {
        return frequencies.stream().anyMatch(item -> item.symbol == symbol);
    }

    public void append(int symbol) {
        if (frequencies.stream().anyMatch(item -> item.symbol == symbol)) {
            frequencies.stream().filter(item -> item.symbol == symbol).forEach(item -> item.frequency++);
        } else {
            frequencies.add(new SymbolFrequency(symbol));
        }
    }

    public void append(int symbol,int frequency) {
        if (frequencies.stream().anyMatch(item -> item.symbol == symbol)) {
            frequencies.stream().filter(item -> item.symbol == symbol).forEach(item -> item.frequency+=frequency);
        } else {
            SymbolFrequency symbolFrequency = new SymbolFrequency(symbol);
            symbolFrequency.frequency = frequency;
            frequencies.add(symbolFrequency);
        }
    }

    public long countBytes(){
        SymbolType symbolType = Constants.READING_SYMBOL_TYPE;
        int symbolSize = 1;
        switch (symbolType) {
            case BYTE:
                symbolSize = 1;
                break;
            case WORD:
                symbolSize = 2;
                break;
            case DOUBLEWORD:
                symbolSize = 4;
                break;
        }

        return size()*symbolSize + size()*4;
    }

    public static class SymbolFrequency {
        public int symbol;
        public int frequency;

        public SymbolFrequency(int symbol) {
            this.symbol = symbol;
            this.frequency = 1;
        }
    }
}
