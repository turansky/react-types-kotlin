// Automatically generated - do not modify!

package tanstack.table.core

external interface GroupingColumnDef<TData : RowData> {
    var aggregationFn: AggregationFnOption<TData>?
    var aggregatedCell: ColumnDefTemplate<() -> Cell.Context<TData>>?
    var enableGrouping: Boolean?
}
