package bg.softuni.orm.context;

import bg.softuni.orm.core.Column;
import bg.softuni.orm.core.Entity;
import bg.softuni.orm.core.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityManager<E> implements DbContext<E> {
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        int id = getId(entity);

        if (id == 0) {
            return doInsert(entity);
        }

        return doUpdate(entity);
    }

    private boolean doUpdate(E entity) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        List<String> columnNames = getColumnNames(entity);
        List<String> values = getEntityValues(entity);

        List<String> updatedValues = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            updatedValues.add(String.format("%s = %s", columnNames.get(i), values.get(i)));
        }

        String sql = String.format("UPDATE %s SET %s WHERE id = %d",
                tableName, String.join(",", updatedValues), getId(entity));

        int i = connection.prepareStatement(sql).executeUpdate();

        return i == 1;
    }

    private boolean doInsert(E entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());
        List<String> columnNames = getColumnNames(entity);
        List<String> values = getEntityValues(entity);

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, String.join(", ", columnNames), String.join(", ", values));

        int updateCount = connection.createStatement().executeUpdate(sql);

        return updateCount == 1;
    }

    private int getId(E entity) throws IllegalAccessException {
        Field idField = getIdField(entity);
        if (idField == null) {
            throw new IllegalArgumentException("Entity must have 1 id field");
        } else {
            idField.setAccessible(true);
            return idField.getInt(entity);
        }
    }

    private String getTableName(Class<?> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null) {
            throw new IllegalArgumentException("Missing entity annotation.");
        }

        return annotation.name();
    }

    private Field getIdField(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class)).findFirst().orElse(null);
    }

    private List<String> getColumnNames(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .map(f -> f.getAnnotation(Column.class))
                .map(Column::name)
                .toList();
    }

    private List<String> getEntityValues(E entity) throws IllegalAccessException {
        List<String> values = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                values.add("'" + field.get(entity).toString() + "'");
            }
        }

        return values;
    }


    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);

        String sql = String.format("SELECT * FROM %s %s",
                tableName, where == null ? "" : where);

        ArrayList<E> result = new ArrayList<>();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            result.add(mapToEntity(table, resultSet));
        }
        return result;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Iterable<E> result = find(table, where == null ? "LIMIT 1" : where + " LIMIT 1");

        if (result.iterator().hasNext()) {
            return  result.iterator().next();
        }
        return null;
    }

    @Override
    public void doCreate(Class<E> table) throws SQLException {
        String tableName = getTableName(table);

        List<String> columns = getAllFieldsAndDataTypes(table);
        String sql = String.format("CREATE TABLE %s (%s);", tableName, String.join(",", columns));

        connection.prepareStatement(sql).executeUpdate();
    }

    @Override
    public void doAlter(Class<E> table) throws SQLException {
        String tableName = getTableName(table);

        List<String> columns = getNewColumns(table);

        if (columns.isEmpty()) {
            return;
        }

        String sql = String.format("ALTER TABLE %s ADD COLUMN %s",
                tableName,
                String.join(",", columns));

        connection.prepareStatement(sql).executeUpdate();
    }

    @Override
    public void doDelete(Class<E> table) throws SQLException {
        String tableName = getTableName(table);

        String sql = String.format("DROP TABLE %s", tableName);

        connection.prepareStatement(sql).executeUpdate();
    }

    private List<String> getNewColumns(Class<?> entityClass) throws SQLException {
        List<String> existingColumns = getExistingColumns(entityClass);
        List<Field> existingFields = Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();

        List<Field> newFields = existingFields.stream()
                .filter(f -> !existingColumns.contains(f.getAnnotation(Column.class).name()))
                .toList();

        List<String> result = new ArrayList<>();
        for (Field field : newFields) {
            result.add(String.format("%s %s",
                    field.getAnnotation(Column.class).name(),
                    getType(field.getType().getSimpleName())));
        }

        return result;
    }

    private List<String> getExistingColumns(Class<?> entityClass) throws SQLException {
        List<String> tableColumns = new ArrayList<>();
        String sql = String.format(
                """
                    SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS
                   WHERE TABLE_SCHEMA = 'soft_uni' AND TABLE_NAME = '%s';""", getTableName(entityClass));
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            tableColumns.add(resultSet.getString("COLUMN_NAME"));
        }
        return tableColumns;
    }

    private List<String> getAllFieldsAndDataTypes(Class<E> table) {

        List<Field> declaredFields = Arrays.stream(table.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
        List<Field> pkFields = Arrays.stream(table.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .toList();

        if (pkFields.size() != 1) {
            throw new IllegalArgumentException("Entity must have 1 id field");
        }

        List<String> result = new ArrayList<>();
        result.add(String.format("%s %s PRIMARY KEY AUTO_INCREMENT",
                pkFields.getFirst().getAnnotation(Column.class).name(),
                getType(pkFields.getFirst().getType().getSimpleName())));

        for (Field field : declaredFields) {
            result.add(String.format("%s %s",
                    field.getAnnotation(Column.class).name(),
                    getType(field.getType().getSimpleName())));
        }

        return result;
    }

    private String getType(String simpleName) {
        return switch (simpleName) {
            case "String" -> "VARCHAR(50)";
            case "int" -> "INT";
            case "LocalDate" -> "DATE";
            default -> "VARCHAR(255)";
        };
    }

    private E mapToEntity(Class<E> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        E result = table.getDeclaredConstructor().newInstance();

        for (Field field : table.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                mapField(result, field, resultSet);
            }
        }

        return result;
    }

    private void mapField(E result, Field field, ResultSet resultSet) throws IllegalAccessException, SQLException {
        String columnName = field.getAnnotation(Column.class).name();

        Object value = mapValue(field, columnName, resultSet);

        field.setAccessible(true);
        field.set(result, value);
    }

    private Object mapValue(Field field, String columnName, ResultSet resultSet) throws SQLException {
        if (field.getType() == int.class || field.getType() == Integer.class) {
            return resultSet.getInt(columnName);
        } else if (field.getType() == String.class) {
            return resultSet.getString(columnName);
        } else if (field.getType() == LocalDate.class) {
            String date = resultSet.getString(columnName);

            return LocalDate.parse(date);
        }

        throw new IllegalArgumentException("Unsupported type " + field.getType());
    }
}
