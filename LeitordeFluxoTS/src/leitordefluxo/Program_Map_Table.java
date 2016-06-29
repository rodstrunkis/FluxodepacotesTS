/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitordefluxo;

import java.util.Arrays;

/**
 *
 * @author Rodolfo
 */
public class Program_Map_Table {
    int table_id ;
    int section_syntax_indicator ;
    int zero;
    int reserved1;
    int section_length;
    int program_number;
    int reserved2;
    int version_number;
    int current_next_indicator;
    int section_number;
    int last_section_number;
    int reserved3;
    int PCR_PID;
    int reserved4;
    int program_info_length;
    int[] stream_type;
    int[] reserved5;
    int[] elementary_PID;
    int[] reserved6;
    int[] ES_info_length;
    long CRC_32;

    @Override
    public String toString() {
            return "PMT [CRC_32=" + CRC_32 + ", ES_info_length="
                            + Arrays.toString(ES_info_length) + ", PCR_PID=" + PCR_PID
                            + ", current_next_indicator=" + current_next_indicator
                            + ", elementary_PID=" + Arrays.toString(elementary_PID)
                            + ", last_section_number=" + last_section_number
                            + ", program_info_length=" + program_info_length
                            + ", program_number=" + program_number + ", reserved1="
                            + reserved1 + ", reserved2=" + reserved2 + ", reserved3="
                            + reserved3 + ", reserved4=" + reserved4 + ", reserved5="
                            + Arrays.toString(reserved5) + ", reserved6="
                            + Arrays.toString(reserved6) + ", section_length="
                            + section_length + ", section_number=" + section_number
                            + ", section_syntax_indicator=" + section_syntax_indicator
                            + ", stream_type=" + Arrays.toString(stream_type)
                            + ", table_id=" + table_id + ", version_number="
                            + version_number + ", zero=" + zero + "]";
    }
}
