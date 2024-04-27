package view;

import view.table.Table;

public interface ViewObserver {

    void updateTable();

    void updateTableModel(Table table);

    void updateButton(Boolean start);
}
