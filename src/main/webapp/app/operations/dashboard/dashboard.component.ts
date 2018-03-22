import { Component, OnInit, OnDestroy } from '@angular/core';
import { CV } from '../cv/cv.model';
import { CVService } from '../cv/cv.service';
import { ITEMS_PER_PAGE } from '../../shared';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html'
}) export class DashboardComponent implements OnInit, OnDestroy {

    cvCards: CV[];
    chartData: any[];
    chartDataAux: any[];
    page: any;
    itemsPerPage: number;
    chartOptions: any;
    chartLabels: string[];

    constructor(
        public cvService: CVService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
        this.cvCards = [];
        this.chartData = [];
        this.chartDataAux = [];
        this.page = 0;
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.chartOptions = {
            responsive: true
        }
        this.chartLabels = ['January', 'February', 'Mars', 'April'];
    }

    ngOnInit() {
        const pageSettings = {
            page: this.page,
            size: this.itemsPerPage
        }
        this.cvService.findAll(pageSettings).subscribe(
            (data: any) => this.onSuccess(data.json(), data.headers),
            (error: Response) => this.onError(error)
        )
        this.chartDataAux = [
            { data: [330, 600, 260, 700], label: 'Account A' },
            { data: [120, 455, 100, 340], label: 'Account B' },
            { data: [45, 67, 800, 500], label: 'Account C' }
        ]
    }

    onSuccess(data: any, headers: any) {
        for (let i = 0; i < data.content.length; i++) {
            if (data.content[i]['status'] === 'RUNNING' && data.content[i]['controlVarEntries'].length > 0) {
                const controlVarEntriesAux: any[] = data.content[i]['controlVarEntries'].slice(Math.max(data.content[i]['controlVarEntries'].length - 5, 0));
                const dataAux: number[] = [];
                for (let j = 0; j < 5; j++) {
                    dataAux.push(controlVarEntriesAux[j]['value']);
                }
                const dato: any = {
                    data: dataAux,
                    label: data.content[i]['name']
                }
                this.chartData.push(dato);
            }
        }
        console.log('chartData:');
        console.log(this.chartData);
        console.log('chartDataAux:');
        console.log(this.chartDataAux);
        // this.links = this.parseLinks.parse(headers.get('link'));
        this.eventManager.broadcast({ name: 'all_success', content: 'OK' });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() { }

}
