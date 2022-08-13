package nextstep.subway.applicaion.dto;

import lombok.Getter;
import nextstep.subway.domain.fare.DiscountPolicy;
import nextstep.subway.domain.fare.Fare;
import nextstep.subway.domain.Path;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int duration;
    private int fare;

    public PathResponse(List<StationResponse> stations, int distance, int duration, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
    }

    public static PathResponse of(Path path, int age) {
        List<StationResponse> stations = path.getStations().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
        int distance = path.extractDistance();
        int duration = path.extractDuration();
        int fare     =  getFare(path, age);

        return new PathResponse(stations, distance, duration, fare);
    }

    private static int getFare(Path path, int age){
        int fare = Fare.calculator(path.getShortestDistance())
                        + path.getLineSurChage();
        fare = DiscountPolicy.calculator(age, fare);
        return fare;
    }

}
