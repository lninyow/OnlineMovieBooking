import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectMallPage extends JPanel {
    private MovieDatabaseManager dbManager;

    public SelectMallPage(MovieDatabaseManager dbManager) {
        this.dbManager = dbManager;
        setPreferredSize(new Dimension(1920, 1080));
        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);

        List<MallData> mallDataList = retrieveMallDataFromDatabase();
        JComboBox<MallData> mallComboBox = createMallComboBox(mainPanel, mallDataList);

        mainPanel.add(mallComboBox, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JComboBox<MallData> createMallComboBox(JPanel mainPanel, List<MallData> mallDataList) {
        JComboBox<MallData> mallComboBox = new JComboBox<>(mallDataList.toArray(new MallData[0]));
        mallComboBox.setRenderer(new MallComboBoxRenderer());
        mallComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                MallData selectedMallData = (MallData) mallComboBox.getSelectedItem();
                // No need to update the picture
            }
        });
        return mallComboBox;
    }

    // Fetch Mall Data from Database
    public List<MallData> retrieveMallDataFromDatabase() {
        List<MallData> mallDataList = new ArrayList<>();

        try {
            Connection connection = dbManager.getDatabaseConnection();
            String query = "SELECT mall_name FROM mall";  // Use your own SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String mallName = resultSet.getString("mall_name");
                mallDataList.add(new MallData(mallName));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mallDataList;
    }

    private class MallData {
        private String mallName;

        public MallData(String mallName) {
            this.mallName = mallName;
        }

        public String getMallName() {
            return mallName;
        }

        @Override
        public String toString() {
            return mallName;
        }
    }

    private static class MallComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof MallData) {
                value = ((MallData) value).getMallName();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }
}