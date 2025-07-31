
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class AIPlayer extends Player{
    Integer Aiplayer_id;
    public AIPlayer(game game , String marker) {
        super(game, marker);
        this.Aiplayer_id = new Random().nextInt();
    }

    @Override 
    public int selectPosition() {
    String opponent_marker = game.getOpponent().getMarker();
    Integer win_block_position = getWinBlockPosition(opponent_marker);
    if (win_block_position != null) return win_block_position;
    if(corner_defense_need(opponent_marker)) {
        int position = corner_trap_defense(opponent_marker);
        return position;
    }
    int position = random_priotity_position();
    return position;
}

    private Map<String, List<Integer>> groupPositionMarker(int[] line) {
        Map<String, List<Integer>> markers = new HashMap<>();
        for(int position : line) {
            String marker = String.valueOf((game.getBoard()[position]));
            markers.computeIfAbsent(marker == null ? "null" : marker, k -> new ArrayList<>()).add(position);
        }
        markers.computeIfAbsent("null", k -> new ArrayList<>());
        return markers;
    }

    private Integer getWinBlockPosition(String opponent_marker) {
        Integer block = null;
        for(int[] line: game.LINES) {
            Map<String, List<Integer>> markers = groupPositionMarker(line);
            if(markers.get("null").size() != 1) continue; // only 1 marker placed in line
            if(markers.getOrDefault(marker, new ArrayList<>()).size()==2) {
                return markers.get("null").get(0); // null is winning position
            } else if (markers.getOrDefault(opponent_marker, new ArrayList<>()).size()==2) {
                block = markers.get("null").get(0); // blocking move
            }
        }
        if(block != null) {
            return block;
        }
        return null;
    }
    

    private boolean corner_defense_need(String opponent_marker) {
        List<Integer> cornerPositions = Arrays.asList(1, 3, 7, 9);
        boolean corner_choice = cornerPositions.stream().anyMatch(pos -> game.getBoard()[pos] != "null"); // search board for 
        return game.getTurnNum() == 2 && corner_choice;
    }

    private int corner_trap_defense(String opponent_marker) {
        int opponent_pos = -1;
        for(int i = 1 ; i<=9 ; i++) {
            if (opponent_marker.equals(game.getBoard()[i])) {
                opponent_pos = i;
                break;
            }
        }
        // corner_trap defenses
        Map<Integer, List<Integer>> safeResponses = new HashMap<>();
        safeResponses.put(1, Arrays.asList(2, 4));
        safeResponses.put(3, Arrays.asList(2, 6));
        safeResponses.put(7, Arrays.asList(4, 8));
        safeResponses.put(9, Arrays.asList(6, 8));
        List<Integer> responses = safeResponses.getOrDefault(opponent_pos, Arrays.asList(5));
        return responses.get(new Random().nextInt(responses.size()));
    }

    private int random_priotity_position() {
        // choose randomly from center & corner
        List<Integer> positions = new ArrayList<>();
        List<Integer> freePositions = game.getFreePositions();
        if (freePositions.isEmpty()) {
            throw new IllegalStateException("No free positions available");
        }
       List<Integer> corners = new ArrayList<>(Arrays.asList(1, 3, 7, 9));
        corners.retainAll(freePositions);
        Collections.shuffle(corners);
        List<Integer> edges = new ArrayList<>(Arrays.asList(2, 4, 6, 8));
        edges.retainAll(freePositions);
        Collections.shuffle(edges);
        positions.addAll(corners);
        positions.addAll(edges);
        return positions.isEmpty() ? freePositions.get(new Random().nextInt(freePositions.size())) : positions.get(0);
    }

    public String toString() {
        return "NPC:- " + Aiplayer_id;
    }
}
