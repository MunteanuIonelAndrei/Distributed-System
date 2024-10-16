import React from 'react';
import { Table } from 'reactstrap';

const MoniDetailsTable = ({ moniDetailsData }) => {
    return (
        <Table hover>
            <thead>
                <tr>
                    <th>Device ID</th>
                    <th>Timestamp</th>
                    <th>Mean Value</th>
                    <th>Max Hours</th>
                    <th>Double Values</th>
                </tr>
            </thead>
            <tbody>
                {moniDetailsData.map((detail, index) => (
                    <tr key={index}>
                        <td>{detail.deviceId}</td>
                        <td>{detail.timestamp}</td>
                        <td>{detail.meanValue}</td>
                        <td>{detail.maxHours}</td>
                        <td>{detail.doubleValues.join(", ")}</td>
                    </tr>
                ))}
            </tbody>
        </Table>
    );
};

export default MoniDetailsTable;
