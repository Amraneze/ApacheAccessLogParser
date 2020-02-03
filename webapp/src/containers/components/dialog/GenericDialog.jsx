import React from 'react';
import { withStyles } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';

import { Textarea } from '../form/';
import { DialogStyle } from '../../../shared/styles/DialogStyle';
import { Utils } from '../../../shared/Utils';

const GenericDialog = (props) => {
    const { classes, props: { fullScreen, open, onClose, dialog, component, isExtended, isCloseIconDisplayed } } = props;
	return (
        <Dialog
            fullScreen={fullScreen}
            open={open}
            onClose={onClose}
            BackdropProps={{
                className: classes.backdrop
            }}
            classes={{
                paper: isExtended ? classes.paperRootFull : classes.paperRoot,
            }}
            aria-labelledby="dialog-title">
            <div className={classes.dialogBar}>
                <DialogTitle id="dialog-title" classes={{ root: classes.dialogTitle }}>{dialog.title}</DialogTitle>
                { isCloseIconDisplayed ? <IconButton edge="start" classes={{ root: classes.closeButton }}
                    color="inherit" onClick={onClose} aria-label="close">
                    <CloseIcon classes={{ root: classes.closeIcon }} />
                </IconButton> : ''}
            </div>
            <DialogContent classes={{ root: isExtended ? classes.genericDialogContentFull : classes.genericDialogContent }}>
                { dialog.text ? <DialogContentText classes={{ root: classes.dialogText }}>
                   <Textarea value={dialog.text} />
                </DialogContentText> : ''}
                { component ? component : ''}
            </DialogContent>
            <DialogActions classes={{ root: classes.dialogActionsRoot }}>
                { !Utils.isEmpty(dialog.buttons.secondary) ? 
                dialog.buttons.secondary.isLink ? <div
                    onClick={dialog.buttons.secondary.handler} className={classes.dialogActionLink}>
                    {dialog.buttons.secondary.text}
                </div>
                : <Button
                    classes={{root: classes.dialogAction}}
                    //className="btn btn__secondary"
                    onClick={dialog.buttons.secondary.handler}
                    color="primary">
                    {dialog.buttons.secondary.text}
                </Button> : '' }
                <Button classes={{root: classes.dialogAction}}
                    //className="btn btn__primary"
                    onClick={dialog.buttons.primary.handler}
                    color="primary" autoFocus>
                    {dialog.buttons.primary.text}
                </Button>
            </DialogActions>
        </Dialog>
	);
};

GenericDialog.defaultProps = {
    props: {
        isExtended: false,
        isCloseIconDisplayed: false,
    },
};

export default withStyles(DialogStyle)(GenericDialog);
