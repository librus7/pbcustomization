-- HEADER-START
-- DESCRIPTION This is the oracle customisation data backup script
--  Note that this script has DROP dtatements that may generate errors when the script is first run -
--  these can be ignored.
-- PARAMETER schema
-- DESCRIPTION Specify the database schema where these tables will be backed up
-- DEFAULT [TRADEIN1]
-- VALUE ${schema}
--
-- HEADER-END
INSERT INTO ${schema}.CHPFLOG ( TIMESEQ, DBVERSION, SOURCE, ACTION, COMMENTS ) VALUES ( TO_CHAR ( SYSDATE, 'YYYY/MM/DD-HH24:MI:SS' ), 'CUSTOM', 'pre_install.sql', 'Starting Custom Creation', 'Executing backup script for extra customisation tables'  );

DROP INDEX ${schema}.X0EXTPMAPI;

DROP INDEX ${schema}.X0EXTEVENT;

DROP INDEX ${schema}.X0EXTEVSTEP;

DROP INDEX ${schema}.X0EXTMASTER;

DROP INDEX ${schema}.X0EXTMSS;

DROP INDEX ${schema}.X0EXTPOSTING;

DROP INDEX ${schema}.X0EXTCUST;

DROP INDEX ${schema}.X0EXTICUST;

DROP INDEX ${schema}.X0EXTEVENTTST;

DROP INDEX ${schema}.X0PIRCRS;


DROP TABLE ${schema}.EXTPMAPI_BACKUP;
ALTER TABLE ${schema}.EXTPMAPI RENAME TO EXTPMAPI_BACKUP;

DROP TABLE ${schema}.EXTEVENT_BACKUP;
ALTER TABLE ${schema}.EXTEVENT RENAME TO EXTEVENT_BACKUP;

DROP TABLE ${schema}.EXTEVSTEP_BACKUP;
ALTER TABLE ${schema}.EXTEVSTEP RENAME TO EXTEVSTEP_BACKUP;

DROP TABLE ${schema}.EXTMASTER_BACKUP;
ALTER TABLE ${schema}.EXTMASTER RENAME TO EXTMASTER_BACKUP;

DROP TABLE ${schema}.EXTMSS_BACKUP;
ALTER TABLE ${schema}.EXTMSS RENAME TO EXTMSS_BACKUP;

DROP TABLE ${schema}.EXTPOSTING_BACKUP;
ALTER TABLE ${schema}.EXTPOSTING RENAME TO EXTPOSTING_BACKUP;

DROP TABLE ${schema}.EXTCUST_BACKUP;
ALTER TABLE ${schema}.EXTCUST RENAME TO EXTCUST_BACKUP;

DROP TABLE ${schema}.EXTICUST_BACKUP;
ALTER TABLE ${schema}.EXTICUST RENAME TO EXTICUST_BACKUP;

DROP TABLE ${schema}.PIRCRS_BACKUP;
ALTER TABLE ${schema}.PIRCRS RENAME TO PIRCRS_BACKUP;

INSERT INTO ${schema}.CHPFLOG ( TIMESEQ, DBVERSION, SOURCE, ACTION, COMMENTS ) VALUES ( TO_CHAR ( SYSDATE+ 1, 'YYYY/MM/DD-HH24:MI:SS' ), 'CUSTOM' , 'pre_install.sql' , 'Custom backup Complete', 'Completed backup script script for extra customisation tables');

COMMIT;
