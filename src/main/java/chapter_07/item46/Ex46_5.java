package chapter_07.item46;

import static java.util.function.BinaryOperator.maxBy;
import static java.util.stream.Collectors.averagingLong;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingLong;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Ex46_5 {
    public static void main(String[] args) {
        //만약 BinaryOperator를 넣어주지 않는다면 ?
//        Map<Artist, Album> error = ALBUMS.stream()
//                .collect(toMap(Album::artist, album -> album));

        Map<Artist, Album> topHits = ALBUMS.stream()
                .collect(toMap(Album::artist, album -> album, maxBy(Comparator.comparing(Album::streamingCounts))));

        Map<Artist, Album> lastWriteWins = ALBUMS.stream()
                .collect(toMap(Album::artist, album -> album, (oldVal, newVal) -> newVal));

        Map<Artist, List<Album>> artistListMap = ALBUMS.stream()
                .collect(groupingBy(Album::artist));

        Map<Artist, Set<Album>> setMap = ALBUMS.stream()
                .collect(groupingBy(Album::artist, toSet()));

        Map<Artist, Long> accumulatedStreamingCounts = ALBUMS.stream()
                .collect(groupingBy(Album::artist, summingLong(Album::streamingCounts)));

//        TreeMap<Artist, Set<Album>> treeMap = ALBUMS.stream()
//                .collect(groupingBy(Album::artist, TreeMap::new, toSet()));

        System.out.println(topHits);
        System.out.println(lastWriteWins);
        System.out.println(artistListMap);
        System.out.println(accumulatedStreamingCounts);

        System.out.println("---");
        Map<Artist, Double> average = ALBUMS.stream()
                .collect(groupingBy(Album::artist, averagingLong(Album::streamingCounts)));
        System.out.println("average = " + average);

        Map<Artist, LongSummaryStatistics> summary = ALBUMS.stream()
                .collect(groupingBy(Album::artist, summarizingLong(Album::streamingCounts)));
        System.out.println("summary = " + summary);

        Map<Artist, List<Album>> filtering = ALBUMS.stream()
                .collect(groupingBy(Album::artist,
                        filtering(album -> album.streamingCounts() <= 1_500_000_000L, toList())));
        System.out.println("filtering = " + filtering);

//        String albumName = ALBUMS.stream().map(Album::name).collect(Collectors.joining());
//        System.out.println("albumName = " + albumName);

//        String albumName = ALBUMS.stream().map(Album::name).collect(Collectors.joining(", "));
//        System.out.println("albumName = " + albumName);

        String albumName = ALBUMS.stream().map(Album::name).collect(Collectors.joining(", ", "[ ", " ]"));
        System.out.println("albumName = " + albumName);
    }

    private static final List<Album> ALBUMS = List.of(
            new Album("강남스타일", new Artist("PSY"), 4_400_000_000L),
            new Album("뚜두뚜두", new Artist("BLACK_PINK"), 1_800_000_000L),
            new Album("붐바야", new Artist("BLACK_PINK"), 1_400_000_000L),
            new Album("Kill This Love", new Artist("BLACK_PINK"), 1_600_000_000L),
            new Album("작은 것들을 위한 시", new Artist("BTS"), 1_550_000_000L),
            new Album("다이너마이트", new Artist("BTS"), 1_520_000_000L),
            new Album("LIKEY", new Artist("TWICE"), 530_000_000L),
            new Album("FANCY", new Artist("TWICE"), 500_000_000L),
            new Album("GENTLEMAN", new Artist("PSY"), 1_400_000_000L)
    );

    static class Album {
        private final String name;
        private final Artist artist;
        private final long streamingCount;

        public Album(String name, Artist artist, long streamingCount) {
            this.name = name;
            this.artist = artist;
            this.streamingCount = streamingCount;
        }

        public String name() {
            return name;
        }

        public Artist artist() {
            return artist;
        }

        public long streamingCounts() {
            return streamingCount;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Album album = (Album) o;
            return streamingCount == album.streamingCount && Objects.equals(name, album.name)
                    && Objects.equals(artist, album.artist);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, artist, streamingCount);
        }
    }


    static class Artist {
        private final String name;

        public Artist(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Artist artist = (Artist) o;
            return Objects.equals(name, artist.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
