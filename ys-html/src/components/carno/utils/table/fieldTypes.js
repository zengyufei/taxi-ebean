import EditableCell from './editableCell'

const fieldTypes = {
    normal: value => value,
    edit: (value, field, record, fn) => {
        return (
            <EditableCell
                value={value}
                onChange={e => (field.editChange ? field.editChange(e, field, record, fn) : fn(e, field, record))}
            />
        )
    },
    number: value => value,
    textarea: value => value,
    datetime: value => {
        return value
    },
    yearMonth: value => {
        return value
    },
    date: value => {
        return value
    },
    enums: (value, field) => {
        if (field.table && field.table.enums) {
            return field.table.enums[value]
        }
        return field.enums[value]
    },
    enum: (value, field) => {
        if (field.table && field.table.enums) {
            return field.table.enums[value]
        }
        return field.enums[value]
    },
    boolean: value => {
        return value === 'true' || value === true ? '是' : '否'
    },
    invalid: value => {
        return +value === 1 ? '有效' : '无效'
    },
    href: value => {
        return <a onClick={() => window.open(value)}>{value}</a>
    },
}

/*
 * 扩展column类型定义
 */
export const tableBindType = types => {
    Object.assign(fieldTypes, types)
}

/*
 * 判断是否已经扩展column类型定义
 */
export const isBindTableType = text => {
    return Object.prototype.hasOwnProperty.call(fieldTypes, text)
}

export default fieldTypes
