import { Component, OnInit, OnDestroy } from '@angular/core';
import { CVService } from '../cv/cv.service';
import { EventService } from '../event/event.service';
import { ResponseWrapper, ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Chart } from 'chart.js';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html'
}) export class DashboardComponent implements OnInit, OnDestroy {

    chartType: string;
    chart: any[];
    CVs: any[];
    events: any[];
    chartDataAux: any[];
    chartOptions: any;
    page: any;
    itemsPerPage: number;

    constructor(
        public cvService: CVService,
        public eventService: EventService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.chartType = '';
        this.chart = [];
        this.CVs = [];
        this.events = [];
        this.chartDataAux = [];
        this.chartOptions = {};
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
    }

    ngOnInit() {
        this.chartType = 'line';
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        };
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccessCV(data.json(), data.headers),
            (error: Response) => this.onError(error)
        );
        this.eventService.findAll(pageSettings).subscribe(
            (res: ResponseWrapper) => this.onSuccessEvent(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
        /*
        this.chartDataAux = [
            { data: [330, 600, 260, 700, 800], label: 'Account A', fill: false },
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
        ];
        */
        this.chartDataAux = [
            {
                data: [{ x: new Date(2018, 1, 1, 12, 50, 55), y: 1 }, { x: new Date(2018, 1, 15, 12, 50, 55), y: 8 }, { x: new Date(2018, 2, 2, 12, 50, 55), y: 3 }],
                label: 'Account A', fill: false
            }
            /*
            { data: [120, 455, 100, 340], label: 'Account B', fill: false },
            { data: [45, 67, 800, 500], label: 'Account C', fill: false }
            */
        ];
        this.chartOptions = {
            responsive: true,
            legend: {
                display: true
            },
            scales: {
                xAxes: [{
                    display: true,
                    type: 'time',
                    time: {
                        displayFormats: {
                            second: 'h:mm:ss a'
                        },
                        unit: 'second'
                    }
                }],
                yAxes: [{
                    display: true
                }],
            }
            /*
            annotation: {
                // Defines when the annotations are drawn.
                // This allows positioning of the annotation relative to the other
                // elements of the graph.
                //
                // Should be one of: afterDraw, afterDatasetsDraw, beforeDatasetsDraw
                // See http://www.chartjs.org/docs/#advanced-usage-creating-plugins
                drawTime: 'afterDatasetsDraw', // (default)

                // Mouse events to enable on each annotation.
                // Should be an array of one or more browser-supported mouse events
                // See https://developer.mozilla.org/en-US/docs/Web/Events
                events: ['click'],

                // Double-click speed in ms used to distinguish single-clicks from
                // double-clicks whenever you need to capture both. When listening for
                // both click and dblclick, click events will be delayed by this
                // amount.
                dblClickSpeed: 350, // ms (default)

                // Array of annotation configuration objects
                // See below for detailed descriptions of the annotation options
                annotations: [{
                    drawTime: 'afterDraw', // overrides annotation.drawTime if set
                    id: 'a-line-1', // optional
                    type: 'line',
                    mode: 'horizontal',
                    scaleID: 'y-axis-0',
                    value: '25',
                    borderColor: 'red',
                    borderWidth: 2
                }]
            }
            */
        };
        const canvas: any = document.getElementById('myChart');
        const ctx = canvas.getContext('2d');
        this.chart = new Chart(ctx, {
            type: this.chartType,
            data: {
                datasets: this.CVs
                // datasets: this.chartDataAux
            },
            options: this.chartOptions
        });
    }

    onSuccessCV(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            if (data.content[i]['status'] === 'RUNNING' && data.content[i]['controlVarEntries'].length > 0) {
                const controlVarEntriesAux: any[] = data.content[i]['controlVarEntries'].slice(Math.max(data.content[i]['controlVarEntries'].length - 5, 0));
                const dataToInsert: any[] = [];
                for (let j = 0; j < 5; j++) {
                    const date: any = new Date(controlVarEntriesAux[j]['creationMoment']);
                    dataToInsert.push({
                        x: new Date(date.getFullYear(), date.getMonth(), date.getDay(), date.getHours(), date.getMinutes(), date.getSeconds()),
                        y: controlVarEntriesAux[j]['value']
                    });
                }
                const dato: any = {
                    data: dataToInsert,
                    label: data.content[i]['name'],
                    backgroundColor: data.content[i]['extractor']['style']['backgroundColor'],
                    fill: false
                }
                this.CVs.push(dato);
            }
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    onSuccessEvent(data: any, headers: any) {
        for (let i = 0; i < data.length; i++) {
            const startingDate: any = new Date(data[i]['startingDate']);
            const dataToInsert: any[] = []
            dataToInsert.push({
                x: new Date(startingDate.getFullYear(), startingDate.getMonth(), startingDate.getDay(),
                    startingDate.getHours(), startingDate.getMinutes(), startingDate.getSeconds())
            });
            const dato: any = {
                data: dataToInsert,
                label: data[i]['name'],
                fill: true,
                type: 'bar'
            }
            this.events.push(dato);
        }
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() { }

}
