package DM19S1;

import java.util.*;

public interface Instruction{
        int update(String donator, Set<Donator> recorade,List<String> lists);
        int donate(String recipient,Set<Donator> recorade,List<String> lists);
        int delete(String donator,Set<Donator> recorade,List<String> lists);
        int query(String name,Set<Donator> recorade,List<String> lists);
        int query(int n,List<Donation>  donations,List<String> lists);
        void process(String line,Set<Donator> recorade,List<String> lists);
        }