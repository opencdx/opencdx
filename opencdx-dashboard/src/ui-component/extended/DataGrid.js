import React, { useState } from 'react';
import { Typography, Paper, Box, Accordion, AccordionSummary, AccordionDetails, Divider } from '@mui/material';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import './Transition.css'; // Create a CSS file with your transition styles

const columns = [
  { field: 'created', headerName: 'Created', flex: 1 },
  { field: 'eventType', headerName: 'Event Type', flex: 1 },
  { field: 'purposeOfUse', headerName: 'Purpose of Use', flex: 1 },
  { field: 'creator', headerName: 'Creator', flex: 1 },
  { field: 'modifier', headerName: 'Modifier', flex: 1 },
  { field: 'modified', headerName: 'Modified', flex: 1 },
  // Add more columns for additional information
];

const sections = [
  { label: 'Actor Information', fields: ['identity_', 'role_', 'networkAddress_', 'agentType_'] },
  { label: 'Audit Entity Information', fields: ['patientIdentifier_', 'userIdentifier_'] },
  { label: 'Audit Source Information', fields: ['configuration_', 'systemInfo_'] },
  { label: 'Data Object Information', fields: ['data_', 'resource_', 'sensitivity_'] },
  // Add more sections as needed
];

const AuditEventTable = ({ auditEventList }) => {
  const [selectedEvent, setSelectedEvent] = useState(null);

  const handleRowClick = (params) => {
    setSelectedEvent(params.row);
  };

  return (
    <Box display="flex">
      <Box flex={1}>
        <DataGrid
          showCellVerticalBorder
          showColumnVerticalBorder
          scrollbarSize={20}
          rows={auditEventList}
          columns={columns}
          checkboxSelection={false}
          getRowId={(row) => row.id}
          onRowClick={handleRowClick}
          sx={{
            boxShadow: 2,
            border: 2,
            borderColor: 'primary.light',
            '& .MuiDataGrid-cell:hover': {
              color: 'primary.main',
            },
            '& .MuiDataGrid-row:hover': {
              backgroundColor: 'primary.light',
            },
            height: '100%',
          }}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: 10,
              },
            },
          }}
          pageSizeOptions={[10, 25, 50, 100]}
          slots={{
            toolbar: GridToolbar,
            noRowsOverlay: () => (
              <Box display="flex" alignItems="center" justifyContent="center">
                <Typography variant="h6">No Audit Events Found</Typography>
              </Box>
            ),
          }}
        />
      </Box>

      {/* Add a separator (Divider) */}
      <Divider orientation="vertical" flexItem />

      <Paper style={{ paddingLeft: '10px', width: '30%' }}>
        {selectedEvent && (
          <Box sx={{ height: '100%', overflow: 'auto' }}>
            <Typography variant="h4" style={{ margin: '10px 0' }}>
              Audit Log Details
            </Typography>
            <TransitionGroup>
              {sections.map((section, index) => (
                <CSSTransition key={index} timeout={500} classNames="fade">
                  <Accordion
                    sx={{
                      border: '1px solid lightgray',
                      minHeight: '20px',
                      '&.Mui-expanded': {
                        minHeight: '48px',
                      },
                      '&:hover': {
                        backgroundColor: 'lightgray',
                        cursor: 'pointer',
                      },
                      height: '100%',
                      transition: 'all 0.5s ease',
                    }}
                  >
                    <AccordionSummary
                      sx={{
                        backgroundColor: '#76B8DF',
                        border: '1px solid lightgray',
                        minHeight: '20px',
                        '&.Mui-expanded': {
                          minHeight: '48px',
                        },
                        '&:hover': {
                          backgroundColor: '#3370A7',
                          cursor: 'pointer',
                        },
                        transition: 'all 0.5s ease',
                      }}
                      expandIcon={<ExpandMoreIcon />}
                      aria-controls="panel1a-content"
                      id="panel1a-header"
                    >
                      <Typography variant="h6">{section.label}</Typography>
                    </AccordionSummary>
                    <AccordionDetails
                      sx={{
                        backgroundColor: '#E5F0FF',
                        border: '1px solid lightgray',
                        minHeight: '20px',
                        '&.Mui-expanded': {
                          minHeight: '48px',
                        },
                        transition: 'all 0.5s ease',
                      }}
                    >
                      {/* Add the fields for the selected event */}
                      {index === 0 && (
                        <div>
                          {section.fields.map((field) => (
                            <p key={field} style={{ margin: '5px 0' }}>{`${field}: ${selectedEvent.actor?.[field]}`}</p>
                          ))}
                        </div>
                      )}
                      {index === 1 && (
                        <div>
                          {section.fields.map((field) => (
                            <p key={field} style={{ margin: '5px 0' }}>{`${field}: ${selectedEvent.auditEntity?.[field]}`}</p>
                          ))}
                        </div>
                      )}
                      {index === 2 && (
                        <div>
                          {section.fields.map((field) => (
                            <p key={field} style={{ margin: '5px 0' }}>{`${field}: ${selectedEvent.auditSource?.[field]}`}</p>
                          ))}
                        </div>
                      )}
                      {index === 3 && (
                        <div>
                          {section.fields.map((field) => (
                            <p key={field} style={{ margin: '5px 0' }}>{`${field}: ${selectedEvent.dataObject?.[field]}`}</p>
                          ))}
                        </div>
                      )}
                    </AccordionDetails>
                  </Accordion>
                </CSSTransition>
              ))}
            </TransitionGroup>
          </Box>
        )}
      </Paper>
    </Box>
  );
};

export default AuditEventTable;
