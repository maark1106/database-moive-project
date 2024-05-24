package view.user;

import dao.user.SearchMovieDao;
import dto.MovieDto;
import dto.UserReservationInfoDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchMovieView extends JFrame {

    private JTextField titleField;
    private JTextField directorField;
    private JTextField actorField;
    private JTextField genreField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private List<MovieDto> movies;
    private Long memberId;
    private JFrame previousFrame;
    private UserReservationInfoDto reservationToChange;

    // Constructor for changing a reservation
    public SearchMovieView(Long memberId, JFrame previousFrame, UserReservationInfoDto reservationToChange) {
        this.memberId = memberId;
        this.previousFrame = previousFrame;
        this.reservationToChange = reservationToChange;
        initUI();
    }

    // Constructor for normal use (no reservation change)
    public SearchMovieView(Long memberId, JFrame previousFrame) {
        this(memberId, previousFrame, null);
    }

    private void initUI() {
        setTitle("Search Movie Service");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                previousFrame.setVisible(true); // 이전 창 다시 표시
            }
        });

        JPanel searchPanel = new JPanel(new GridLayout(5, 2));

        searchPanel.add(new JLabel("영화명:"));
        titleField = new JTextField();
        searchPanel.add(titleField);

        searchPanel.add(new JLabel("감독명:"));
        directorField = new JTextField();
        searchPanel.add(directorField);

        searchPanel.add(new JLabel("배우명:"));
        actorField = new JTextField();
        searchPanel.add(actorField);

        searchPanel.add(new JLabel("장르:"));
        genreField = new JTextField();
        searchPanel.add(genreField);

        JButton searchButton = new JButton("조회");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMovies();
            }
        });
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "영화명", "상영시간", "감독명", "배우명", "장르", "설명", "개봉일", "평점", "연령제한"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        JButton showScheduleButton = new JButton("영화 상영 일정 확인");
        showScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMovieSchedule();
            }
        });
        add(showScheduleButton, BorderLayout.SOUTH);
    }

    private void searchMovies() {
        String title = titleField.getText();
        String director = directorField.getText();
        String actor = actorField.getText();
        String genre = genreField.getText();

        SearchMovieDao dao = new SearchMovieDao();
        movies = dao.searchMovies(title, director, actor, genre);

        tableModel.setRowCount(0);
        for (MovieDto movie : movies) {
            Object[] rowData = {
                    movie.id(),
                    movie.title(),
                    movie.runningTime(),
                    movie.director(),
                    movie.actor(),
                    movie.genre(),
                    movie.description(),
                    movie.releaseDate(),
                    movie.rating(),
                    movie.ageLimit()
            };
            tableModel.addRow(rowData);
        }
    }

    private void showMovieSchedule() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "상영 일정을 보기 위해 영화를 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long movieId = (Long) tableModel.getValueAt(selectedRow, 0);
        new MovieSchedulingInfoView(movieId, memberId, this, reservationToChange).setVisible(true);
        setVisible(false); // 현재 창 숨기기
    }
}
