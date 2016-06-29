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
public class Program_Association_Table {
    int table_id;
    int section_syntax_indicator;
    int zero;
    int reserved1;
    int section_length;
    int transport_stream_id;
    int reserved2;
    int version_number;
    int current_next_indicator;
    int section_number;
    int last_section_number;
    int[] network_PID; //network_PID é opcional
    int[] program_map_PID;
    long CRC_32;
    @Override
    public String toString() {
            return "PAT [current_next_indicator=" + current_next_indicator
                            + ", last_section_number=" + last_section_number
                            + ", program_map_PID=" + Arrays.toString(program_map_PID)
                            + ", reserved1=" + reserved1 + ", reserved2=" + reserved2
                            + ", section_length=" + section_length + ", section_number="
                            + section_number + ", section_syntax_indicator="
                            + section_syntax_indicator + ", table_id=" + table_id
                            + ", transport_stream_id=" + transport_stream_id
                            + ", version_number=" + version_number + ", zero=" + zero + "]";
    }
}
